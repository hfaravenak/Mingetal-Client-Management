import React from "react";
import "./App.css";

import AuthProvider from "./services/AuthProvider";
import Routes from "./components/Routes";


function App() {
  return (
      <AuthProvider>
        <Routes />
      </AuthProvider>
  );
}

export default App;
