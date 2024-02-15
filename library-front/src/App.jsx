import "./index.scss"
import {Route,Routes} from "react-router-dom"
import Home from "./pages/Home/Home";
import Book from "./pages/Book/Book";

function App() {
  return (



    <div>
        <Routes>
          <Route path="/" element={<Home></Home>} ></Route>
          <Route path="/book/:id" element={<Book></Book>} ></Route>
        </Routes>
    </div>
  );
}

export default App;

