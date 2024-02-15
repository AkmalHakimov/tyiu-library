import React from "react";
import logo_img from "../../assets/images/logo.png";
import "../Book/Book.scss";
import Fouter from "../../layouts/Fouter/Fouter";
import qrcode_img from "../../assets/images/qrcode.png";

export default function Book() {
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
            <div className="card_book">
              <img width={150} height={150} src={qrcode_img} alt="" />
              <p>
                B. Abdullayeva - Boshlang'ich matematika nazariyasining tarixiy
              </p>
              <button className="button">Yuklab Olish</button>
            </div>
          </div>
        </div>
      </div>
      <Fouter></Fouter>
    </div>
  );
}
