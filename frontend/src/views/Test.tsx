import React from 'react';
// import './App.css';
import {Button, message} from "antd";
import {result} from "../types/axios";
import request from "../api/request";

const Test: React.FC = () => {
    const testFunction = async () => {
        const res: result = await request.get("/userManager/getPages", {
            params: {
                email: "",
                userName: "",
                telephone: "",
                status: "",
                userType: "Wrong",
                createTimeFirst: "",
                createTimeLast: "",
                current: 1,
                size: 10
            }
        });

        console.log("qwzcxxxxxxxxxxxxxxxxxxxxxxxxxxxxxer");
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
        if (res.code !== 200) {
            message.error('登录失败');
            return;
        }
    }

  return (
    <div className="Test">
      高手
        <Button type={"primary"} onClick={testFunction}>废物</Button>
    </div>
  );
}

export default Test;
