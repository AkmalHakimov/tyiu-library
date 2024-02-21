import "./index.scss";
import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home/Home";
import Book from "./pages/Book/Book";
import HomeAdmin from "./Admin/HomeAdmin/HomeAdmin";
import Adabiyotlar from "./Admin/Adabiyotlar/Adabiyotlar";
import AdminCategory from "./Admin/AdminCategory/AdminCategory";
import AddAdabiyotlar from "./Admin/Adabiyotlar/AddAdabiyotlar/AddAdabiyotlar";

function App() {
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
      </Routes>
    </div>
  );
}

export default App;
