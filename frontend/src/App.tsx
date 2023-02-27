import React from 'react';
import 'reset-css';
import './App.css';
import './assets/styles/global.scss';
import BeforeRouterEnter from "./router/BeforeRouterEnter";

const App: React.FC = () => {
  return (
    <div className="App">
      <BeforeRouterEnter />
    </div>
  );
}

export default App;
