import React from 'react';
// import './App.css';
import {Button, message} from "antd";
import result from "../types/axios";
import request from "../api/request";

const Test: React.FC = () => {
    const testFunction = async () => {
        const res: result = await request.get("/userManager/getPages", {
            params: {
                email: ""
            }
        });

        console.log("qwer");
        let postForm = {
            userName: "321951493@qq.com",
            password: "123456"
        };
        // const res: result = await request.post('/user/login', postForm);
        // if (res.code !== 200) {
        //     message.error(res.msg);
        //     return;
        // }
        console.log(res);
    }

  return (
    <div className="Test">
      高手
        <Button type={"primary"} onClick={testFunction}>废物</Button>
    </div>
  );
}

export default Test;
