import React from 'react';
import { Button, Result } from 'antd';
import {Link} from "react-router-dom";
import styles from './error.module.scss';

const Error: React.FC = () => {
    return (
        <Result
            className={styles.result}
            status="404"
            title="404"
            subTitle="抱歉，您访问的页面不存在！"
            extra={
                <Link to={"/"}>
                    <Button type="primary">返回首页</Button>
                </Link>
            }>
        </Result>
    );
}

export default Error;
