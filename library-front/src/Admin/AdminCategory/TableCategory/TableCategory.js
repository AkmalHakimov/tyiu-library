import React, { useContext, useEffect, useRef, useState } from "react";
import {connect} from "react-redux"
import { Button, Modal, Form, Input, Popconfirm, Table } from "antd";
import "../TableCategory/TableCategory.scss";
import ApiRequest from "../../../configure/ApiRequestor/ApiRequest";
import { categoryActions } from "../Redux/Reducers/CategoryReducer";


const TableCategory = (props) => {

  useEffect(() => {
    props.getCategories();
  }, []);

  const handleDelete = (id) => {
    ApiRequest.delete(`/category/${id}`).then((res) => {
      props.getCategories();
    }).catch(()=>{});
  };

  function handleEdit(item) {
    props.setIsModal();
    props.setCategoryInp(item.name);
    props.setCurrentItm(item);
  }
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
        props.dataSource?.length >= 1 ? (
          <Button onClick={() => handleEdit(record)} type="primary">
            Edite
          </Button>
        ) : null,
    },
  ];
  const handleOk = () => {
    if (props.currentItm) {
      ApiRequest({
        url: "/category/" + props.currentItm.id,
        method: "put",
        data:  {
          name: props.categoryInp,
        }
      }).then((res) => {
        props.setCurrentItm("");
        props.getCategories();
      }).catch(()=>{});
    } else {
      ApiRequest({
       url:  "/category",
       method:"post",
       data: {
        name: props.categoryInp,
      }}).then((res) => {
        props.getCategories();
      }).catch(()=>{});
    }
    props.setCategoryInp("");
    props.setIsModal()
  };
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
      }} loading={props.loading} bordered dataSource={props.dataSource} columns={columns} />
      <Modal
        title="Yo'nalish"
        open={props.isModalOpen}
        onOk={handleOk}
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
