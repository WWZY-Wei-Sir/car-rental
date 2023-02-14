import React from 'react';
// import './App.css';
import {Button} from "antd";
import {Link} from "react-router-dom";

function Home() {
    return (
        <div className="Home">
            <Link to={"/Test"}>Go To Test</Link>
        </div>
    );
}

export default Home;
