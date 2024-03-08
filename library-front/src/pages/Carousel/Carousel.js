import React from "react";
import { useEffect } from "react";
import { Carousel } from "antd";
import "../Carousel/carousel.scss";
import content1_image from "../../assets/images/carousel1.jpeg";
import content2_image from "../../assets/images/carousel2.jpeg";
import content3_image from "../../assets/images/carousel3.jpeg";

const App = () => {

  return(
    <>
  <Carousel autoplay speed={2000} autoplaySpeed={5000} draggable effect="fade" >
    <div>
      <section className="content">
        <div className="container_box">
          <div className="left">
            <h3 >TURKISTON YANGI INNOVATSIYALAR UNIVERSITETI</h3>
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
            <h3>TURKISTON YANGI INNOVATSIYALAR UNIVERSITETI</h3>
            <p>ELEKTRON KUTUBXONA TIZIMI</p>
          </div>
          <div className="right">
            <img className="image" src={content2_image} alt="" />
          </div>
        </div>
      </section>
    </div>
    <div>
    <section className="content">
        <div className="container_box">
          <div className="left">
            <h3>TURKISTON YANGI INNOVATSIYALAR UNIVERSITETI</h3>
            <p>ELEKTRON KUTUBXONA TIZIMI</p>
          </div>
          <div className="right">
            <img className="image" src={content3_image} alt="" />
          </div>
        </div>
      </section>
    </div>
  </Carousel>
  </>
  )};
export default App;
