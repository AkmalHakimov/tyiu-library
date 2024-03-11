import React, { useEffect, useState } from "react";
import logo_img from "../../assets/images/logo.png";
import "../Book/Book.scss";
import Fouter from "../../layouts/Fouter/Fouter";
import qrcode_img from "../../assets/images/qrcode.png";
import ApiRequest from "../../configure/ApiRequestor/ApiRequest";
import { Link, useParams } from "react-router-dom";
import ViewFile from "../../utils/ViewFile/ViewFile";
import DownloadFile from "../../utils/DownloadFile/DownloadFile";


export default function Book() {
  const [books, setBooks] = useState([]);
  const params = useParams();

  useEffect(() => {
    getBooks();
  }, []);

  function getBooks() {
    ApiRequest.get(`/book?categoryId=${params.id}`).then((res) => {
      setBooks(res.data.content);
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
            {books.length !== 0 ? (
              <>
                {books?.map((item, index) => {
                  return (
                    <div key={index} className="card_book">
                      <img
                        width={150}
                        height={150}
                        src={
                          "http://localhost:8080/api/qrCode/" + item.qrCodeId
                        }
                        alt=""
                      />
                      <p>
                        <b>
                          <p style={{
                            overflow: "hidden",
                            whiteSpace: "nowrap",
                            textOverflow: "ellipsis",
                          }}>{item.author.slice(0,60)}</p> {item.name}
                        </b>
                      </p>
                      <p>
                        <span className="text-primary">Kitob toifasi: </span>{" "}
                        {item.categoryName}
                      </p>
                      <button
                        onClick={() => DownloadFile(item)}
                        className="button"
                      >
                        Yuklab Olish
                      </button>
                    </div>
                  );
                })}{" "}
              </>
            ) : (
              <h1 className="text-danger">
                BU BO'LIMDA HOZIRCHA KITOB JOYLANMAGAN! KEYINROQ TEKSHIRIB
                KO'RING!!!
              </h1>
            )}
          </div>

          <Link to={"/"} className="link_style">
            <p style={{color: "#4f8db3"}}>Bosh sahifaga qaytish</p>
          </Link>
        </div>
      </div>
      <Fouter></Fouter>
      
    </div>
  );
}
