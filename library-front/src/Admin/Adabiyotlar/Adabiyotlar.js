import React from "react";
import { Breadcrumb, Layout, Menu, theme } from "antd";
import TableAdabiyotlar from "./TableAdabiyotlar/TableAdabiyotlar";
import Item from "antd/es/list/Item";

export default function Adabiyotlar() {
  const { Header, Content, Footer, Sider } = Layout;

  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();
  return (
    <Layout>
      <Content
        style={{
          margin: "0 16px",
        }}
      >
          <h1>Adabiyotlar</h1>
        <div
          style={{
            padding: 24,
            minHeight: 360,
            background: colorBgContainer,
            borderRadius: borderRadiusLG,
          }}
        >
          <TableAdabiyotlar />
        </div>
      </Content>
      <Footer
        style={{
          textAlign: "center",
        }}
      ></Footer>
    </Layout>
  );
}
