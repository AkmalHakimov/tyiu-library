import React from "react";
import { Layout,theme,Typography } from "antd";
import TableAdabiyotlar from "./TableAdabiyotlar/TableAdabiyotlar";

export default function Adabiyotlar() {
  const { Header, Content, Footer, Sider } = Layout;
  const { Title } = Typography;
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
            <div style={{
                width: "100%",
                paddingTop: 10
              }}><Title>Adabiyotlar</Title></div>
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
