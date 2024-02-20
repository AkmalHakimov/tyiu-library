import React, { useEffect, useState } from "react";
import Carousel from "../Carousel/Carousel";
import "../Home/Home.scss";
import { Card } from "antd";
import book_img from "../../assets/images/diamond.png.png";
import "bootstrap/dist/css/bootstrap.min.css";
import ApiRequest from "../../utils/ApiRequest";
import Footer from "../../layouts/Fouter/Fouter"
import { useNavigate } from "react-router-dom";

export default function Home() {
  const [categories, setCategories] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getCategories();
  }, []);

  function getCategories() {
    ApiRequest("/category", "get").then((res) => {
      setCategories(res.data.content);
    });
  }

  function GoToBook(categoryId){
    navigate("/book/" + categoryId)
  }
  return (
    <div>
      <Carousel></Carousel>
      <div className="main-section">
        <div className="container">
          <div className="top-text">
            <h3>
              Xush kelibsiz, Turkiston Yangi Innovatsiyalari universitetining
              elektron kutubxonasiga!
            </h3>

            <div>
              <p>Sizga qaysi yo'nalish uchun kitoblar kerakligini tanlang!</p>

              <hr
                style={{
                  height: "3px",
                  border: "none",
                  backgroundColor: "black",
                  borderRadius: "5px",
                }}
              />
            </div>
          </div>

          <div className="main-content">
            {categories.map((item, index) => {
              return (
                <div key={index} className="card_category">
                  <img src={book_img} alt="" />
                  <p>{item.name}</p>
                  <button onClick={()=>GoToBook(item.id)} className="button">Kitoblar</button>
                </div>
              );
            })}
          </div>
        </div>

        <div className="white-box">
            
        </div>
      </div>
      <Footer></Footer>
    </div>
    
  );
}
