import React, { useContext, useEffect, useRef, useState } from "react";
import { Button,Modal, Form, Input, Popconfirm, Table } from "antd";
import "../TableCategory/TableCategory.css";
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
  const [dataSource, setDataSource] = useState([
    {
      key: "0",
      name: "Edward King 0",
      age: "32",
      address: "London, Park Lane no. 0",
    },
    {
      key: "1",
      name: "Edward King 1",
      age: "32",
      address: "London, Park Lane no. 1",
    },
  ]);
  const handleDelete = (key) => {
    console.log("salom");
  };

  function handleEdit(item){
    showModal()
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
      dataIndex: "",
      render: (_, record) =>
        dataSource.length >= 1 ? (
          <Popconfirm
            title="Sure to delete?"
            onConfirm={() => handleDelete(record.key)}
          >
            <Button type="primary">Delete</Button>
          </Popconfirm>
        ) : null,
    },
    {
        title: "Edite",
        dataIndex: "",
        render: (_, record) =>
          dataSource.length >= 1 ? (
              <Button onClick={()=>handleEdit(record)} type="primary">Edite</Button>
          ) : null,
      },
  ];
  const handleAdd = () => {
    showModal()
    console.log("bu add");
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
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    setIsModalOpen(false);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

  useEffect(()=>{
    getCategories();
  },[])

  function getCategories(){
    ApiRequest("/category","get").then(res=>{
        setDataSource(res.data)
    })
  }
  return (
    <div>
      <Button
        onClick={handleAdd}
        type="primary"
        style={{
          marginBottom: 16,
        }}
      >
        Add a row
      </Button>
      <Table
        bordered
        dataSource={dataSource}
        columns={columns}
      />
      <Modal title="Yo'nalish" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
            <Input placeholder="name"></Input>
      </Modal>
    </div>
  );
};
export default TableCategory;
