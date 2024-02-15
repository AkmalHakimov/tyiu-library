import React from "react";
import { useEffect } from "react";
import { Carousel } from "antd";
import "../Carousel/carousel.scss";
import content1_image from "../../assets/images/slide1.png.png";

const App = () => (

   
  <Carousel>
    <div>
      <section className="content">
        <div className="container_box">
          <div className="left">
            <h3>TURKISTON YANGI INNOVATSIYALAR UNIVERSITETI</h3>
            <p>ELEKTRON KUTUBXONA TIZIMI</p>
          </div>
          <div className="right">
            <img className="image" src={content1_image} alt="" />
          </div>
        </div>
      </section>
    </div>
    <div>
    <section className="content">
        <div className="container_box">
          <div className="left">
            <h1>TURKISTON YANGI INNOVATSIYALAR UNIVERSITETI</h1>
            <p>ELEKTRON KUTUBXONA TIZIMI</p>
          </div>
          <div className="right">
            <img className="image" src={content1_image} alt="" />
          </div>
        </div>
      </section>
    </div>
    <div>
    <section className="content">
        <div className="container_box">
          <div className="left">
            <h1>TURKISTON YANGI INNOVATSIYALAR UNIVERSITETI</h1>
            <p>ELEKTRON KUTUBXONA TIZIMI</p>
          </div>
          <div className="right">
            <img className="image" src={content1_image} alt="" />
          </div>
        </div>
      </section>
    </div>
    <div>
    <section className="content">
        <div className="container_box">
          <div className="left">
            <h1>TURKISTON YANGI INNOVATSIYALAR UNIVERSITETI</h1>
            <p>ELEKTRON KUTUBXONA TIZIMI</p>
          </div>
          <div className="right">
            <img className="image" src={content1_image} alt="" />
          </div>
        </div>
      </section>
    </div>
  </Carousel>
);
export default App;
