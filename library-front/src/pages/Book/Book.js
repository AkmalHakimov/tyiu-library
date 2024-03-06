import React, { useEffect, useState } from "react";
import logo_img from "../../assets/images/logo.png";
import "../Book/Book.scss";
import Fouter from "../../layouts/Fouter/Fouter";
import qrcode_img from "../../assets/images/qrcode.png";
import ApiRequest from "../../utils/ApiRequest";
import { useParams } from "react-router-dom";
import ViewFile from "../../utils/ViewFile/ViewFile";

export default function Book() {
  const [books, setBooks] = useState([]);
  const [bookCategory,setBookCategory] = useState("");
  const params = useParams();

  useEffect(() => {
    getBooks();
    getCategories();
  }, []);

  function getBooks() {
    ApiRequest.get(`/book?categoryId=${params.id}`).then((res) => {
      setBooks(res.data.content);
    });
  }

  function getCategories() {
    ApiRequest.get(`/category/all`).then((res) => {
      setBookCategory(res.data);
    });
  }

  return (
    <div>
      <div className="header">
        <div className="container_box">
          <div className="content_box">
            <img width={80} height={80} src={logo_img} alt="" />
            <h3>O'zingizga kerak kitobni tanlang!</h3>
          </div>
        </div>
      </div>

      <div className="main_section">
        <div className="container_box">
          <div className="content_box">
            {books?.map((item, index) => {
              return (
                <div key={index} className="card_book">
                  <img width={150} height={150} src={"http://localhost:8080/api/qrCode/" + item.qrCodeId } alt="" />
                  <p>
                    <b>{item.author}-{item.name}</b>
                  </p>
                  <p><span className="text-primary">Kitob toifasi: </span>  {bookCategory?.name}</p>
                  <button onClick={()=>ViewFile(item)} className="button">Yuklab Olish</button>
                </div>
              );
            })}
          </div>
        </div>
      </div>
      <Fouter></Fouter>
    </div>
  );
}
