import React from 'react'
import { Breadcrumb,Layout, Menu, theme } from "antd";
export default function AddAdabiyotlar() {

    const { Header, Content, Footer, Sider } = Layout;


    const {
        token: { colorBgContainer, borderRadiusLG },
      } = theme.useToken();
  return (
    <div>
         <Layout>
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
            <h1>Adabiyotlar Qo'shish</h1>
            </Breadcrumb>
            <div
              style={{
                padding: 24,
                minHeight: 360,
                background: colorBgContainer,
                borderRadius: borderRadiusLG,
              }}
            >
              
            </div>
          </Content>
          <Footer
            style={{
              textAlign: "center",
            }}
          ></Footer>
        </Layout>
    </div>
  )
}
