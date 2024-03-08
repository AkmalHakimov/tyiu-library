import "./index.scss";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import Home from "./pages/Home/Home";
import Book from "./pages/Book/Book";
import HomeAdmin from "./Admin/HomeAdmin/HomeAdmin";
import Adabiyotlar from "./Admin/Adabiyotlar/Adabiyotlar";
import AdminCategory from "./Admin/AdminCategory/AdminCategory";
import Login from "./pages/Login/Login";
import axios from "axios";
import { BASE_URL } from "./utils/ApiRequest";
import { useEffect } from "react";
import NotFound from "./pages/404/NotFound";

function App() {

  const location = useLocation();
  const navigate = useNavigate()


  function securityFrontend(){
    if(location.pathname.startsWith("/admin")){
      if(localStorage.getItem("access_token")!==null){
        axios({
          url:BASE_URL + "/user/me",
          method: "get",
          headers: {
            Authorization: localStorage.getItem("access_token"),
        },
        }).then(res=>{
          let s = res.data.roles.filter(item=>item.roleName==="ROLE_ADMIN")[0]
          if(!s){
            navigate("/404")
          }
        }) 
      }else {
          navigate("/404")
      }
    }
  }

  useEffect(()=>{
    securityFrontend()
  },[location.pathname])
  return (
    <div>

      <Routes>
        <Route path="/" element={<Home></Home>}></Route>
        <Route path="/admin" element={<HomeAdmin></HomeAdmin>}>
          <Route
            path="adabiyotlar"
            element={<Adabiyotlar></Adabiyotlar>}
          ></Route>
          <Route
            path="yo'nalishlar"
            element={<AdminCategory></AdminCategory>}
          ></Route>
          {/* <Route
            path="adabiyotlar/add"
            element={<AddAdabiyotlar></AddAdabiyotlar>}
          ></Route> */}
        </Route>
        <Route path="/book/:id" element={<Book></Book>}></Route>
        <Route path="/login" element={<Login></Login>}></Route>
        <Route path="/404" element={<NotFound></NotFound>}></Route>
      </Routes>
    </div>
  );
}

export default App;
