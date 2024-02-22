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
} from "antd";
import ApiRequest from "../../../utils/ApiRequest";
import { UploadOutlined } from "@ant-design/icons";
import { FileOutlined } from "@ant-design/icons";
import dayjs from "dayjs";
export default function TableAdabiyotlar() {
  const [data, setData] = useState([]);
  const [totalPages, setTotalPages] = useState("");
  const [loading, setLoading] = useState(false);
  const [searchInp, setSearchInp] = useState("");
  const [selectVal, setSelectVal] = useState(0);
  const [page, setPage] = useState(1);
  const [modalVisible, setModalVisible] = useState(false);
  const [currentItm, setCurrentItm] = useState("");
  const [savedFile, setSavedFile] = useState("");
  const [hasFile, setHasFile] = useState("");
  const [form] = Form.useForm();

  const [options, setOptions] = useState([
    { label: "Yo'nalishlar", value: "" },
  ]);

  const columns = [
    {
      title: "#",
      dataIndex: "id",
      sorter: true,
      render: (name) => `${name}`,
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
      render: (bookDate) => new Date(bookDate).getFullYear(),
      with: "30%",
    },
    {
      title: "Delete",
      dataIndex: "",
      render: (item) => (
        <Button type="primary" onClick={() => delItem(item.id)}>
          Delete
        </Button>
      ),
      with: "30%",
    },
    {
      title: "Edit",
      dataIndex: "",
      render: (item) => (
        <Button type="primary" onClick={() => editItm(item)}>
          Edit
        </Button>
      ),
      with: "30%",
    },
    {
      title: "View",
      dataIndex: "",
      render: (item) => (
        <Button type="primary" onClick={() => viewFile(item)}>
          View
        </Button>
      ),
      with: "30%",
    },
  ];

  useEffect(() => {
    getBooks(page);
    getCategories();
  }, []);

  function delItem(bookId) {
    ApiRequest("/book/" + bookId, "delete").then((res) => {
      getBooks(page);
    });
  }

  function editItm(item) {
    setModalVisible(true);
    form.setFieldValue("categoryId", {
      label: item.categoryName,
      value: item.categoryId,
    });
    form.setFieldValue("name", item.name);
    form.setFieldValue("author", item.author);
    form.setFieldValue("publisher", item.publisher);
    form.setFieldValue("bookDate", dayjs(item.bookdate));
    form.setFieldValue("description", item.description);
    setCurrentItm(item);
  }

  function getCategories() {
    ApiRequest("/category/all", "get").then((res) => {
      res.data.map((item, index) => {
        options.push({
          label: item.name,
          value: item.id,
          key: index,
        });
        setOptions([...options]);
      });
    });
  }
  function getBooks(page, search = searchInp, select = selectVal) {
    setLoading(true);
    ApiRequest(
      `/book?page=${page}&categoryId=${select}&search=` + search,
      "get"
    ).then((res) => {
      setTotalPages(res.data.totalElements);
      setData(res.data.content);
      setLoading(false);
    });
  }

  function openModal() {
    setModalVisible(true);
  }
  function onFinish(value) {
    let obj = {
      name: value.name,
      author: value.author,
      description: value.description,
      publisher: value.publisher,
      bookDate: value.bookDate,
      pdfId: hasFile,
      categoryId: value.categoryId.value
        ? value.categoryId.value
        : value.categoryId,
    };
    if (currentItm) {
      ApiRequest(`/book?bookId=${currentItm.id}`, "put", obj).then((res) => {
        getBooks(page);
        setCurrentItm("");
      });
    } else {
      ApiRequest("/book", "post", obj).then((res) => {
        getBooks(page);
      });
    }
    setModalVisible(false);
    form.resetFields();
    setSavedFile("")
  }

  function handleFile(info) {
    let formData = new FormData();
    formData.append("file", info.file);
    formData.append("prefix", "/kitoblar/pdfs");
    ApiRequest("/file", "post", formData).then((res) => {
    message.success(`${info.file.name} file uploaded successfully`);
      setSavedFile(info.file);
      setHasFile(res.data);
    });
  }

  function viewFile(item) {
    ApiRequest(`/file?id=${item.pdfId}`, "get")
      .then(response => {
        const blobRes = new Blob([response.data]);
        const blobURL = URL.createObjectURL(blobRes);
        window.open(blobURL, '_blank');
      })
      .catch(error => {
        console.error('Error fetching or opening the PDF:', error);
      });
  }
  

  // const props = {
  //   name: 'file',
  //   action: 'https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188',
  //   headers: {
  //     authorization: 'authorization-text',
  //   },
  //   onChange(info) {
  //     if (info.file.status === 'done') {

  //     } else if (info.file.status === 'error') {
  //       console.log(info.file);
  //       message.error(`${info.file.name} file upload failed.`);
  //     }
  //   },
  // };

  return (
    <div>
      <div className="mb-4 d-flex align-items-center justify-content-between">
        <div
          style={{ width: 450 }}
          className="d-flex align-items-center justify-content-between"
        >
          <Button onClick={openModal} type="primary">
            Add a row
          </Button>
          <Select
            defaultValue="Yo'nalishlar"
            onChange={(val) => {
              setSelectVal(val);
              getBooks(page, searchInp, val);
            }}
            style={{
              width: 300,
            }}
            options={options}
          />
        </div>
        <div>
          <Input
            value={searchInp}
            onChange={(e) => {
              setSearchInp(e.target.value);
              getBooks(page, e.target.value);
            }}
            placeholder="search..."
          />
        </div>
      </div>
      <Table
        pagination={{
          pageSize: 6,
          total: totalPages,
          onChange: (page) => {
            getBooks(page);
          },
        }}
        loading={loading}
        columns={columns}
        dataSource={data}
      />
      <Modal
        title="Adabiyot qo'shish"
        width={1000}
        centered
        open={modalVisible}
        onCancel={() => {
          setModalVisible(false);
          form.resetFields();
          setSavedFile("");
        }}
        footer={[
          <Button
            key="cancel"
            onClick={() => {
              setModalVisible(false);
              form.resetFields();
              setSavedFile("")
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
            onFinish={onFinish}
            //   requiredMark={requiredMark === 'customize' ? customizeRequiredMark : requiredMark}
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
              <Select defaultValue="Yo'nalishlar" options={options} />
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
            <Form.Item
              rules={[{ required: true, message: "Sana tanlash majburiy" }]}
              required
              label="Sanasi"
              name="bookDate"
            >
              <DatePicker
                style={{
                  width: "100%",
                }}
              />
            </Form.Item>
            <Form.Item label="Upload File" name="attachmentId">
              <Upload
                accept=".pdf"
                progress={false}
                customRequest={handleFile}
                showUploadList={false}
                maxCount={1}
              >
                <Button icon={<UploadOutlined />}>Click to Upload</Button>
              </Upload>
              {savedFile ? (
                <Space className="d-flex">
                  <FileOutlined />
                  <Typography>{savedFile.name}</Typography>
                </Space>
              ) : (
                ""
              )}
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
