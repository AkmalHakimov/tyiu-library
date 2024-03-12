import React, { useEffect } from "react";
import {connect} from "react-redux"
import { Button, Modal, Input, Popconfirm, Table } from "antd";
import "../TableCategory/TableCategory.scss";
import { categoryActions } from "../Redux/Reducers/CategoryReducer";

const TableCategory = (props) => {

  useEffect(() => {
    props.getCategories();
  }, []);

  const columns = [
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
        props.dataSource?.length >= 1 ? (
          <Popconfirm
            title="Sure to delete?"
            onConfirm={() => props.handleDel(record.id)}
          >
            <Button type="primary">Delete</Button>
          </Popconfirm>
        ) : null,
    },
    {
      title: "Edit",
      dataIndex: "",
      render: (_, record) =>
        props.dataSource?.length >= 1 ? (
          <Button onClick={() => props.handleEdit(record)} type="primary">
            Edit
          </Button>
        ) : null,
    },
  ];
  return (
    <div>
      <div className="d-flex justify-content-between align-items-center">
        <Button
          onClick={()=>props.setIsModal()}
          type="primary"
          style={{
            marginBottom: 16,
          }}
        >
          Add a row
        </Button>
        <div>
          <Input value={props.searchInp} onChange={(e)=>{props.setSearchInp(e.target.value); props.getCategories()}} placeholder="search by name"></Input>
        </div>
      </div>
      <Table pagination={{
        pageSize:6,
        total: props.totalPages,
        onChange: (page)=>{
          props.setPage(page)
          props.getCategories()
        } 
      }} loading={props.loading} bordered dataSource={props.dataSource.map((item) => ({
        ...item,
        key: item.id, // Assuming 'id' is a unique identifier for each item
        // Other properties...
      }))} columns={columns} />
      <Modal
        title="Yo'nalish"
        open={props.isModalOpen}
        onOk={()=>props.handleSave()}
        onCancel={()=>props.setIsModal()}
      >
        <Input
          value={props.categoryInp}
          onChange={(e) =>props.setCategoryInp(e.target.value)}
          placeholder="name"
        ></Input>
      </Modal>
    </div>
  );
};
export default connect((state=>state.category),categoryActions)(TableCategory);
