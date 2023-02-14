import React, {useEffect, useState} from 'react';
import {Button, Checkbox, Form, Image, Input, Modal, Space} from 'antd';
import styles from './entrancePage.module.scss';
import entranceTitle from '../../assets/entranceTitle.png';

const EntrancePage: React.FC = () => {
    const [loginForm] = Form.useForm();
    const [registerForm] = Form.useForm();

    const [isLogin, setIsLogin] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const onFinish = (values: any) => {
        console.log('Success:', values);
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    const showModal = () => {
        setIsModalOpen(true);
    };

    const handleOk = () => {
        setIsModalOpen(false);
    };

    const handleCancel = () => {
        setIsModalOpen(false);
    };

    const turnToLogin = () => {
        setIsLogin(true);
        document.getElementsByTagName('body')[0].style.setProperty('--login-font-weight', "bold");
        document.getElementsByTagName('body')[0].style.setProperty('--register-font-weight', "normal");
        document.getElementsByTagName('body')[0].style.setProperty('--login-border-bottom-color', "red");
        document.getElementsByTagName('body')[0].style.setProperty('--register-border-bottom-color', "white");
    }

    const turnToRegister = () => {
        setIsLogin(false);
        document.getElementsByTagName('body')[0].style.setProperty('--login-font-weight', "normal");
        document.getElementsByTagName('body')[0].style.setProperty('--register-font-weight', "bold");
        document.getElementsByTagName('body')[0].style.setProperty('--login-border-bottom-color', "white");
        document.getElementsByTagName('body')[0].style.setProperty('--register-border-bottom-color', "red");
    }

    useEffect(() => {
        console.log("qwer: " + isLogin);
    }, [isLogin]);

    return (
        <div className={styles.entrancePage}>
            <img className={styles.entranceLogo} alt="汽车租赁管理系统 LOGO" src={entranceTitle} />
            <div className={styles.container}>
                <Space>
                    <p className={styles.loginText} onClick={turnToLogin}>登陆界面</p>
                    <p className={styles.registerText} onClick={turnToRegister}>注册界面</p>
                </Space>
                {
                    isLogin ? (
                        <div className={styles.login}>
                            <div className={styles.loginForm}>
                                <Form
                                    form={loginForm}
                                    name="loginForm"
                                    requiredMark={false}
                                    labelCol={{ span: 2 }}
                                    wrapperCol={{ span: 20 }}
                                    style={{ maxWidth: 600 }}
                                    initialValues={{ remember: true }}
                                    onFinish={onFinish}
                                    onFinishFailed={onFinishFailed}
                                    autoComplete="off"
                                >
                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="username"
                                        rules={[{ required: true, message: 'Please input your username!' }]}
                                    >
                                        <Input className={styles.zxc} placeholder={"请输入用户名"} />
                                    </Form.Item>

                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="password"
                                        rules={[{ required: true, message: 'Please input your password!' }]}
                                    >
                                        <Input.Password placeholder={"请输入密码"} />
                                    </Form.Item>

                                    <p className={styles.forgetPwd} onClick={showModal}>忘记密码，通过邮箱重置</p>

                                    <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                                        <Space>
                                            <Button type="primary" htmlType="submit">
                                                登录
                                            </Button>
                                            <Button htmlType="reset">
                                                清空
                                            </Button>
                                        </Space>
                                    </Form.Item>
                                </Form>
                            </div>
                        </div>
                    ) : (
                        <div className={styles.register}>
                            <div className={styles.registerForm}>
                                <Form
                                    form={registerForm}
                                    name="registerForm"
                                    requiredMark={false}
                                    labelCol={{ span: 2 }}
                                    wrapperCol={{ span: 20 }}
                                    style={{ maxWidth: 600 }}
                                    initialValues={{ remember: true }}
                                    onFinish={onFinish}
                                    onFinishFailed={onFinishFailed}
                                    autoComplete="off"
                                >
                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="username"
                                        rules={[{ required: true, message: 'Please input your username!' }]}
                                    >
                                        <Input placeholder={"邮箱"} />
                                    </Form.Item>

                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="username"
                                        rules={[{ required: true, message: 'Please input your username!' }]}
                                    >
                                        <Input placeholder={"用户名"} />
                                    </Form.Item>

                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="password"
                                        rules={[{ required: true, message: 'Please input your password!' }]}
                                    >
                                        <Input.Password placeholder={"密码"} />
                                    </Form.Item>

                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="password"
                                        rules={[{ required: true, message: 'Please input your password!' }]}
                                    >
                                        <Input.Password placeholder={"确认密码"} />
                                    </Form.Item>

                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="username"
                                        rules={[{ required: true, message: 'Please input your username!' }]}
                                    >
                                        <Space>
                                            <Input placeholder={"用户名"} />
                                            <Button htmlType="button">
                                                获取验证码
                                            </Button>
                                        </Space>
                                    </Form.Item>

                                    <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                                        <Space>
                                            <Button type="primary" htmlType="submit">
                                                注册
                                            </Button>
                                            <Button htmlType="reset">
                                                清空
                                            </Button>
                                        </Space>
                                    </Form.Item>
                                </Form>
                            </div>
                        </div>
                    )
                }

                <div>
                    <Modal title="Find Forget Password" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
                        <p>Some contents...</p>
                        <p>Some contents...</p>
                        <p>Some contents...</p>
                    </Modal>
                </div>
            </div>
        </div>
    );
}

export default EntrancePage;
