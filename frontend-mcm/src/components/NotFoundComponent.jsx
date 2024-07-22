import React from "react";
import styled from "styled-components";

import HeaderLoginComponents from "./Headers/HeaderLoginComponents";

function NotFoundComponent() {

   return (
      <div>
         <HeaderLoginComponents></HeaderLoginComponents>
         <NavStyle>
            <div className="container_main">
               No tiene acceso
            </div>
         </NavStyle>
      </div>
   );
}

export default NotFoundComponent;

const NavStyle = styled.nav`
   .container_main {
      display: flex;
      justify-content: space-between;
      align-items: center;
      flex-wrap: wrap;
      margin: 2%;
      padding: 2%;
      border: 2px solid #d5d5d5;
      background-color: #f0f0f0;
   }
   .card {
      background-color: #fff;
      display: flex;
      flex-direction: column;
      align-items: center;
      margin: 10px;
      padding: 10px;
      width: 250px;
      height: 250px;
      border: 1px solid black;
      border-radius: 50px;
      transition: transform 0.5s ease;
   }
   .img-card {
      width: 50%;
      height: 50%;
      object-fit: cover;
      margin-bottom: 10px;
   }
   .card h2 {
      font-size: 20px;
      font-weight: bold;
      text-align: center;
      margin: 0;
      padding: 20px;
   }

   .card:hover {
      transform: scale(1.1);
      cursor: pointer;
   }

   .card-info {
      display: flex;
      align-items: center;
   }

   .alerta-icon {
      width: 20px;
      height: 20px;
      border: 1px solid black;
      border-radius: 100%;
      padding: 2px;
   }
`;
