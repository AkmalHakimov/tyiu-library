import React, { useEffect, useState } from "react";
import {
  Form,
  DatePicker,
  Select,
  Table,
  Button,
  Space,
  Input,
  Modal,
  message,
  Upload,
  Typography,
  Popconfirm,
} from "antd";
import ApiRequest from "../../../configure/ApiRequestor/ApiRequest";
import { UploadOutlined } from "@ant-design/icons";
import { FileOutlined } from "@ant-design/icons";
import ViewFile from "../../../utils/ViewFile/ViewFile";
import DownloadExcel from "../../../utils/DownloadExcel/DownloadExcel";
import { connect, useDispatch } from "react-redux";
import { BooksActions } from "../Redux/Reducers/BooksReducer";
import { categoryActions } from "../../AdminCategory/Redux/Reducers/CategoryReducer";

function TableAdabiyotlar(props) {
  const [form] = Form.useForm();
  const dispatch = useDispatch();
  const [savedFile,setSavedFile] = useState("")

  useEffect(() => {
    props.getBooks();
    dispatch(categoryActions.getAllCategories());
  }, []);

  useEffect(() => {
    if(!props.modalVisible){
      form.resetFields();
    }
  }, [props.modalVisible]);

  const columns = [
    {
      title: "#",
      dataIndex: "id",
      render: (text, name,index) => (props.pageSize * (props.page - 1)) + (index + 1),
      width: "10%",
    },
    {
      title: "Kitob nomi",
      dataIndex: "name",
      with: "30%",
    },
    {
      title: "Kitob muallifi",
      dataIndex: "author",
      with: "30%",
    },
    {
      title: "Kitob sanasi",
      dataIndex: "bookDate",
      with: "30%",
    },
    {
      title: "Delete",
      dataIndex: "",
      render: (item) =>
        !!props.data.length && (
          <Popconfirm
            title="Sure to delete?"
            onConfirm={() => props.delItem(item.id)}
          >
            <Button type="primary">Delete</Button>
          </Popconfirm>
        ),
      with: "30%",
    },
    {
      title: "Edit",
      dataIndex: "",
      render: (item) => (
        <Button type="primary" onClick={()=>handleEdit(item)}>
          Edit
        </Button>
      ),
      with: "30%",
    },
    {
      title: "View",
      dataIndex: "",
      render: (item) => (
        <Button type="primary" onClick={() => ViewFile(item)}>
          View
        </Button>
      ),
      with: "30%",
    },
  ];

  function handleEdit(item){
  props.setModalVisible();
  form.setFieldsValue({...item,categoryId: {
    label: item.categoryName,
    value: item.categoryId,
  }}) 
  props.setCurrentItm(item);
  }


  function handleFile(info) { 
    let formData = new FormData();
    formData.append("file", info.file);
    // formData.append("prefix", "/kitoblar/pdfs");
    formData.append("prefix", "/kitoblar/pdfs_temp");
      ApiRequest(
      "/file",
      "post",
      formData,
    ).then((res) => {
        message.success(`${info.file.name} file uploaded successfully`);
        setSavedFile(info.file);
        props.setHasFile(res.data);
      })
  }

  return (
    <div>
      <div className="mb-4 d-flex align-items-center justify-content-between">
        <div
          style={{ width: 450 }}
          className="d-flex align-items-center justify-content-between"
        >
          <Button onClick={() => props.setModalVisible()} type="primary">
            Add a row
          </Button>
          <Select
            defaultValue="Yo'nalishlar"
            onChange={(val) => {
              props.setSelectVal(val);
              props.getBooks();
            }}
            style={{
              width: 300,
            }}
            options={props.options}
          />
        </div>
        <div className="d-flex gap-3">
          <Button onClick={DownloadExcel} type="primary">
            Download Excel
          </Button>
          <Input
            value={props.searchInp}
            onChange={(e) => {
              props.setSearchInp(e.target.value);
              props.getBooks();
            }}
            placeholder="search..."
          />
        </div>
      </div>
      <Table
        pagination={{
          pageSize: props.pageSize,
          showSizeChanger: true,
          total: props.totalPages,
          onChange: (paginationPage, pageSize) => {
            props.setPage(paginationPage);
            props.setPageSize(pageSize);
            props.getBooks();
          },
          current: props.page,
        }}
        loading={props.loading}
        columns={columns}
        dataSource={props.data.map((item) => ({
          ...item,
          key: item.id, // Assuming 'id' is a unique identifier for each item
        }))}
      />
      <Modal
        title="Adabiyot qo'shish"
        width={1000}
        centered
        open={props.modalVisible}
        onCancel={() => {
          props.setModalVisible();
          form.resetFields();
          setSavedFile("");
        }}
        footer={[
          <Button
            key="cancel"
            onClick={() => {
              props.setModalVisible();
              form.resetFields();
              setSavedFile("");
            }}
          >
            Cancel
          </Button>,
          <Button
            key="submit"
            form="adabiyotForm" // ID of the form
            htmlType="submit"
            type="primary"
          >
            Submit
          </Button>,
        ]}
      >
        <div>
          <Form
            form={form}
            layout="vertical"
            id="adabiyotForm"
            initialValues={{ categoryId: "Yo'nalishlar" }} // Set initial values here
            onFinish={(value)=>props.handleSave({value})} 
          >
            <Form.Item
              name="categoryId"
              rules={[
                { required: true, message: "Yo'nalish tanlash majburiy" },
              ]}
              required
              label="Yo'nalish tanlang"
              hasFeedback
            >
              <Select options={props.options} />
            </Form.Item>
            <Form.Item
              label="Kitob Nomi"
              rules={[
                { required: true, message: "Kitob ismini tanlash majburiy" },
              ]}
              required
              name="name"
            >
              <Input placeholder="text" />
            </Form.Item>
            <Form.Item
              required
              rules={[{ required: true, message: "Muallif tanlash majburiy" }]}
              name="author"
              label="Muallif"
            >
              <Input placeholder="text" />
            </Form.Item>

            <Form.Item
              rules={[
                { required: true, message: "Nashriyot tanlash majburiy" },
              ]}
              required
              label="Nashriyot"
              name="publisher"
            >
              <Input placeholder="text" />
            </Form.Item>
            <Form.Itemx
              rules={[{ required: true, message: "Sana tanlash majburiy" }]}
              required
              label="Sanasi"
              name="bookDate"
            >
              <Input placeholder="text" />
              {/* <DatePicker
                style={{
                  width: "100%",
                }}
              /> */}
            </Form.Itemx>
            <Form.Item label="Upload File" name="attachmentId">
              <div>
              <Upload
                accept=".pdf"
                progress={false}
                customRequest={(info)=>handleFile(info)}
                showUploadList={false}
                maxCount={1}
              >
                <Button icon={<UploadOutlined />}>Click to Upload</Button>
              </Upload>
              {savedFile.name ? (
                <Space className="d-flex">
                  <FileOutlined />
                  <Typography>{savedFile.name}</Typography>
                </Space>
              ) : (
                ""
              )}
              </div>
            </Form.Item>
            <Form.Item label="Izoh" name="description">
              <Input.TextArea />
            </Form.Item>
          </Form>
        </div>
      </Modal>
    </div>
  );
}

export default connect((state) => state.Books, { ...BooksActions })(
  TableAdabiyotlar
);
