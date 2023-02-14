import React from 'react';
import 'reset-css';
import './App.css';
import './assets/styles/global.scss';
import router from './router';
import {useRoutes} from "react-router-dom";

const App: React.FC = () => {
  return (
    <div className="App">
        {useRoutes(router)}
    </div>
  );
}

export default App;
