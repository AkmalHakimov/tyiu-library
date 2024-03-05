import React, { useContext, useEffect, useRef, useState } from "react";
import { Button, Modal, Form, Input, Popconfirm, Table } from "antd";
import "../TableCategory/TableCategory.scss";
import ApiRequest from "../../../utils/ApiRequest";
const EditableContext = React.createContext(null);
const EditableRow = ({ index, ...props }) => {
  const [form] = Form.useForm();
  return (
    <Form form={form} component={false}>
      <EditableContext.Provider value={form}>
        <tr {...props} />
      </EditableContext.Provider>
    </Form>
  );
};
const EditableCell = ({
  title,
  editable,
  children,
  dataIndex,
  record,
  handleSave,
  ...restProps
}) => {
  const [editing, setEditing] = useState(false);
  const inputRef = useRef(null);
  const form = useContext(EditableContext);
  useEffect(() => {
    if (editing) {
      inputRef.current.focus();
    }
  }, [editing]);
  const toggleEdit = () => {
    setEditing(!editing);
    form.setFieldsValue({
      [dataIndex]: record[dataIndex],
    });
  };
  const save = async () => {
    try {
      const values = await form.validateFields();
      toggleEdit();
      handleSave({
        ...record,
        ...values,
      });
    } catch (errInfo) {
      console.log("Save failed:", errInfo);
    }
  };
  let childNode = children;
  if (editable) {
    childNode = editing ? (
      <Form.Item
        style={{
          margin: 0,
        }}
        name={dataIndex}
        rules={[
          {
            required: true,
            message: `${title} is required.`,
          },
        ]}
      >
        <Input ref={inputRef} onPressEnter={save} onBlur={save} />
      </Form.Item>
    ) : (
      <div
        className="editable-cell-value-wrap"
        style={{
          paddingRight: 24,
        }}
        onClick={toggleEdit}
      >
        {children}
      </div>
    );
  }
  return <td {...restProps}>{childNode}</td>;
};
const TableCategory = () => {
  const [dataSource, setDataSource] = useState([]);
  const [searchInp,setSearchInp] = useState("");
  const handleDelete = (id) => {
    ApiRequest.delete(`/category/${id}`).then((res) => {
      getCategories(page);
    }).catch(()=>{});
  };

  function handleEdit(item) {
    showModal();
    setCategoryInp(item.name);
    setCurrentItm(item);
  }
  const defaultColumns = [
    {
      title: "id",
      dataIndex: "id",
      width: "10%",
    },
    {
      title: "name",
      dataIndex: "name",
    },
    {
      title: "Delete",
      dataIndex: "delete",
      render: (_, record) =>
        dataSource?.length >= 1 ? (
          <Popconfirm
            title="Sure to delete?"
            onConfirm={() => handleDelete(record.id)}
          >
            <Button type="primary">Delete</Button>
          </Popconfirm>
        ) : null,
    },
    {
      title: "Edite",
      dataIndex: "",
      render: (_, record) =>
        dataSource?.length >= 1 ? (
          <Button onClick={() => handleEdit(record)} type="primary">
            Edite
          </Button>
        ) : null,
    },
  ];
  const handleAdd = () => {
    showModal();
  };
  const columns = defaultColumns.map((col) => {
    if (!col.editable) {
      return col;
    }
    return {
      ...col,
      onCell: (record) => ({
        record,
        editable: col.editable,
        dataIndex: col.dataIndex,
        title: col.title,
      }),
    };
  });

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [categoryInp, setCategoryInp] = useState("");
  const [loading,setLoading] = useState(false);
  const [currentItm, setCurrentItm] = useState("");
  const [totalPages,setTotalPages] = useState("");
  const [page,setPage] = useState(1)
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    if (currentItm) {
      ApiRequest({
        url: "/category/" + currentItm.id,
        method: "put",
        data:  {
          name: categoryInp,
        }
      }).then((res) => {
        setCurrentItm("");
        getCategories(page);
      });
    } else {
      ApiRequest({
       url:  "/category",
       method:"post",
       data: {
        name: categoryInp,
      }}).then((res) => {
        getCategories(page);
      }).catch(()=>{});
    }
    setCategoryInp("");
    setIsModalOpen(false);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

  useEffect(() => {
    getCategories(page);
  }, []);

  function getCategories(pageParam,search = searchInp) {
    setLoading(true)
    setPage(pageParam)
    ApiRequest.get(`/category?page=${pageParam}&search=`+ (search)).then((res) => {
      setDataSource(res.data.content)
      setTotalPages(res.data.totalElements)
    }).catch(()=>{})
    .finally(() => {
      setLoading(false);
    });
  }
  return (
    <div>
      <div className="d-flex justify-content-between align-items-center">
        <Button
          onClick={handleAdd}
          type="primary"
          style={{
            marginBottom: 16,
          }}
        >
          Add a row
        </Button>
        <div>
          <Input value={searchInp} onChange={(e)=>{setSearchInp(e.target.value); getCategories(page,e.target.value)}} placeholder="search by name"></Input>
        </div>
      </div>
      <Table pagination={{
        pageSize:6,
        total: totalPages,
        onChange: (page)=>{
          getCategories(page)
        } 
      }} loading={loading} bordered dataSource={dataSource} columns={columns} />
      <Modal
        title="Yo'nalish"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Input
          value={categoryInp}
          onChange={(e) => setCategoryInp(e.target.value)}
          placeholder="name"
        ></Input>
      </Modal>
    </div>
  );
};
export default TableCategory;
