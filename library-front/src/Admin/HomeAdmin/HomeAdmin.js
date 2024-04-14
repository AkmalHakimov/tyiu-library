import React, { useState } from "react";
import { FaRegUser } from "react-icons/fa";
import { MdLogout } from "react-icons/md";
import { CgProfile } from "react-icons/cg";
import { MdOutlineArticle } from "react-icons/md";
import { MdOutlineAudioFile } from "react-icons/md";
import { FaUserEdit } from "react-icons/fa";
import { IoHomeOutline, IoTimeOutline } from "react-icons/io5";
import { PiBookOpen } from "react-icons/pi";
import { Breadcrumb, Layout, Menu, theme } from "antd";
import Adabiyotlar from "../Adabiyotlar/Adabiyotlar";
import DropDown from "../../utils/DropDown/DropDown";
import { Outlet, useNavigate } from "react-router-dom";

const { Header, Content, Footer, Sider } = Layout;
function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}
const HomeAdmin = () => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate();
 

  function handleClickMenu(item) {
    navigate(item.key);
  }
  return (
    <>
      <Header style={{display: "flex", justifyContent: "center",alignItems: "center", color: "white" }}><h1>Admin Panel</h1></Header>
      <Layout
        style={{
          minHeight: "100vh",
        }}
      >
        <Sider
          collapsible
          collapsed={collapsed}
          onCollapse={(value) => setCollapsed(value)}
        >
          <div className="demo-logo-vertical" />
          <Menu
            theme="dark"
            defaultSelectedKeys={["adabiyotlar"]}
            mode="inline"
            onClick={(item) => handleClickMenu(item)}
            items={[
              {
                label: "Asosiy",
                key: "asosiy",
                icon: <IoHomeOutline />,
                children: [
                  {
                    label: "Adabiyotlar",
                    key: "/admin/adabiyotlar",
                    icon: <PiBookOpen />,
                  },
                  {
                    label: "Maqolalar",
                    key: "maqolalar",
                    icon: <MdOutlineArticle />,
                  },
                  {
                    label: "AudiKitoblar",
                    key: "audiokitoblar",
                    icon: <MdOutlineAudioFile />,
                  },
                ],
              },
              {
                label: "User",
                key: "user",
                icon: <FaRegUser />,
                children: [
                  {
                    label: "Profil",
                    key: "profil",
                    icon: <CgProfile />,
                  },
                  {
                    label: "Sozlamalar",
                    key: "configuration",
                    icon: <FaUserEdit />,
                  },
                  {
                    label: "chiqish",
                    key: "chiqish",
                    danger: true,
                    icon: <MdLogout />,
                  },
                ],
              },
              {
                label: "Sozlamalar",
                key: "sozlamalar",
                icon: <IoHomeOutline />,
                children: [
                  {
                    label: "Yo'nalishlar",
                    key: "/admin/yo'nalishlar",
                    icon: <PiBookOpen />,
                  },
                ],
              },
            ]}
          />
        </Sider>
        <Outlet></Outlet>
      </Layout>
    </>
  );
};
export default HomeAdmin;
