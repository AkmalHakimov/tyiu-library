import React from 'react'
import { Breadcrumb,Layout, Menu, theme } from "antd";


export default function Adabiyotlar() {
const { Header, Content, Footer, Sider } = Layout;


    const {
        token: { colorBgContainer, borderRadiusLG },
      } = theme.useToken();
  return (
        <Layout>
          <Header
            style={{
              padding: 0,
              background: colorBgContainer,
            }}
          />
          <Content
            style={{
              margin: "0 16px",
            }}
          >
            <Breadcrumb
              style={{
                margin: "16px 0",
              }}
            >
              <Breadcrumb.Item>User</Breadcrumb.Item>
              <Breadcrumb.Item>Bill</Breadcrumb.Item>
            </Breadcrumb>
            <div
              style={{
                padding: 24,
                minHeight: 360,
                background: colorBgContainer,
                borderRadius: borderRadiusLG,
              }}
            >
                <h1>Salom</h1>
            </div>
          </Content>
          <Footer
            style={{
              textAlign: "center",
            }}
          ></Footer>
        </Layout>
  )
}
