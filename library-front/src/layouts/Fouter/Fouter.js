import React from "react";
import "../../layouts/Fouter/Fouter.scss"

export default function Fouter() {
  const ContentStyle = {
    width: "500px",
    height: "170px",
    border: 0
  };

  return (
    <div className="parent-box">
      <div className="container-box">
        <div className="top">
          <div className="left">
            <iframe
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1533.7771905255322!2d64.40475323572527!3d39.74965967083213!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3f50074f159cff07%3A0x7d7ef125d48083aa!2sTURKISTON%20YANGI%20INNOVATSIYALAR%20UNIVERSITETI!5e0!3m2!1sru!2s!4v1707936241019!5m2!1sru!2s"
              style={ContentStyle}
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
            ></iframe>
          </div>
          <div className="right">
            <h4>Bog'lanish</h4>
            <hr />
            <p><b>Turkiston Yangi Innovatsiyalar Universiteti</b></p>
            <p>Telefon: +998 91 305 00 09</p>
            <p>Buxoro shahar, Yangiobod mfy, G`ijduvon ko'chasi, 74-uy.</p>
          </div>
        </div>

        <div className="bottom">
          <hr />
          <p style={{textAlign: "center",marginBottom:0}}>© {new Date().getFullYear()} tyiu.uz.</p>
        </div>
      </div>
    </div>
  );
}
