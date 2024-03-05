import React from "react";
import { Button, Checkbox, Form, Input } from 'antd';
import ApiRequest from "../../utils/ApiRequest";
import axios from "axios";
import { ErrorNotify } from "../../utils/ErrorNotify/ErrorNotify";

export default function LoginUser() {
  const onFinish = (values) => {
    axios({
        url:"http://localhost:8080/api/auth/login",
        method:"post",
        data:{
            userName: values.userName,
            password: values.password
        }
    }).then((res)=>{
        console.log(res);
    }).catch(err=>{
        ErrorNotify(err.response.data)  
    })
  };
  return (
    <div className="my-5" style={{marginLeft:"400px"}}>
      <Form
        name="basic"
        labelCol={{
          span: 8,
        }}
        wrapperCol={{
          span: 16,
        }}
        style={{
          maxWidth: 600,

        }}
        initialValues={{
          remember: true,
        }}
        onFinish={onFinish}
        autoComplete="off"
      >
        <Form.Item
          label="Username"
          name="userName"
          rules={[
            {
              required: true,
              message: "Please input your username!",
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[
            {
              required: true,
              message: "Please input your password!",
            },
          ]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item
          name="remember"
          valuePropName="checked"   
          wrapperCol={{
            offset: 8,
            span: 16,
          }}
        >
          <Checkbox>Remember me</Checkbox>
        </Form.Item>

        <Form.Item
          wrapperCol={{
            offset: 8,
            span: 16,
          }}
        >
          <Button type="primary" htmlType="submit">
            Submit
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
}
