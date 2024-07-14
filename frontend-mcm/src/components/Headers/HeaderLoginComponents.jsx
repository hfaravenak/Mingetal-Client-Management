import React from "react";
import Logo from "../../images/logo.jpg";
import styled from "styled-components";

function HeaderLoginComponents() {

   return (
      <div>
         <NavStyle>
            <header className="header">
               <div className="header_izq">
                  <div className="logo">
                     <img style={{ width: "100px" }} id="Logo" src={Logo} alt="Logo" />
                  </div>                
               </div>
            </header>
         </NavStyle>
      </div>
   );
}

export default HeaderLoginComponents;

const NavStyle = styled.nav`
   .header {
      display: flex;
      justify-content: space-between; /* Para separar los elementos a los extremos */
      padding: 10px;
      background-color: #61c9f9;
      color: white;
   }
   .header_izq {
      display: flex;
      align-items: center;
      text-align: center;
   }
   .header .btn {
      display: inline-block;
      padding: 10px 20px;
      color: #ebfcff;
      text-decoration: none; /* Elimina la subrayado del enlace */
      margin-right: 10px;
      font-family: "Pacifico", serif;
      font-weight: bold;
      font-size: 20px;
   }

   .header .btn:hover {
      color: #00375e;
      cursor: pointer;
   }

   .logo:hover {
      cursor: pointer;
   }
`;
