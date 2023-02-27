import React, {useState} from 'react';
import {Button, Form, Input, message, Modal, Space} from 'antd';
import request from '../../api/request';
import styles from './entrancePage.module.scss';
import entranceTitle from '../../assets/entranceTitle.png';
import result from "../../types/axios";
import {useNavigate} from "react-router-dom";

const EntrancePage: React.FC = () => {
    const navigate = useNavigate();

    const [entranceForm] = Form.useForm();

    const [isLogin, setIsLogin] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const toLogin = async (values: any) => {
        const res: result = await request.post('/user/login', values);
        if (res.code !== 200) {
            message.error(res.msg);
            return;
        }
        const user = JSON.parse(JSON.stringify(res.data));
        localStorage.setItem("user_info", JSON.stringify(user.userPrincipal));
        localStorage.setItem("user_token", user.user_token);

        message.success("登陆成功！");
        entranceForm.resetFields();
        navigate("/Home");
    }

    const toRegister = async (values: any) => {
        const res: result = await request.post('/user/register', values);
        if (res.code !== 200) {
            message.error(res.msg);
            return;
        }

        message.success("注册成功！");
        entranceForm.resetFields();
        turnToLogin();
    };

    const toResetPwd = async (values: any) => {
        const res: result = await request.post('/user/chgPwd', values);
        if (res.code !== 200) {
            message.error(res.msg);
            return;
        }

        message.success("修改密码成功！");
        handleCancel();
    };

    const getPassCode = async () => {
        const email: string = entranceForm.getFieldValue('email');
        if (email === undefined) {
            return;
        }

        const res: result = await request.get('/mail/sendPassCode', {
            params: {
                mailbox: email
            }
        });
        if (res.code !== 200) {
            message.error(res.msg);
            return;
        }

        message.success("发送验证码成功！");
    };

    const showModal = () => {
        entranceForm.resetFields();
        setIsModalOpen(true);
    };

    const handleCancel = () => {
        entranceForm.resetFields();
        setIsModalOpen(false);
    };

    const turnToLogin = () => {
        setIsLogin(true);
        document.getElementsByTagName('body')[0].style.setProperty('--login-font-weight', "bold");
        document.getElementsByTagName('body')[0].style.setProperty('--register-font-weight', "normal");
        document.getElementsByTagName('body')[0].style.setProperty('--login-border-bottom-color', "red");
        document.getElementsByTagName('body')[0].style.setProperty('--register-border-bottom-color', "white");
        entranceForm.resetFields();
    }

    const turnToRegister = () => {
        setIsLogin(false);
        document.getElementsByTagName('body')[0].style.setProperty('--login-font-weight', "normal");
        document.getElementsByTagName('body')[0].style.setProperty('--register-font-weight', "bold");
        document.getElementsByTagName('body')[0].style.setProperty('--login-border-bottom-color', "white");
        document.getElementsByTagName('body')[0].style.setProperty('--register-border-bottom-color', "red");
        entranceForm.resetFields();
    }

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
                                    form={entranceForm}
                                    name="entranceForm"
                                    requiredMark={false}
                                    labelCol={{ span: 2 }}
                                    wrapperCol={{ span: 20 }}
                                    style={{ maxWidth: 600 }}
                                    initialValues={{ remember: true }}
                                    onFinish={toLogin}
                                    autoComplete="off"
                                >
                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="userName"
                                        rules={[{ required: true, message: 'Please input your username!' }]}
                                    >
                                        <Input placeholder={"请输入用户名"} />
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
                                    form={entranceForm}
                                    name="entranceForm"
                                    requiredMark={false}
                                    labelCol={{ span: 2 }}
                                    wrapperCol={{ span: 20 }}
                                    style={{ maxWidth: 600 }}
                                    initialValues={{ remember: true }}
                                    onFinish={toRegister}
                                    autoComplete="off"
                                >
                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="email"
                                        rules={[{ required: true, message: 'Please input your username!' }]}
                                    >
                                        <Input placeholder={"邮箱"} />
                                    </Form.Item>

                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="userName"
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
                                        name="checkPassword"
                                        rules={[{ required: true, message: 'Please input your password!' }]}
                                    >
                                        <Input.Password placeholder={"确认密码"} />
                                    </Form.Item>

                                    <Form.Item
                                        label=" "
                                        colon={false}
                                        name="passCode"
                                        rules={[{ required: true, message: 'Please input your username!' }]}
                                    >
                                        <Space>
                                            <Input placeholder={"验证码"} />
                                            <Button htmlType="button" className={styles.getPassCode} onClick={getPassCode}>
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
                    <Modal width={400} centered title="通过邮箱找回密码" open={isModalOpen} onCancel={handleCancel} footer={false}>
                        <div className={styles.resetPwdForm}>
                            <Form
                                form={entranceForm}
                                name="entranceForm"
                                requiredMark={false}
                                labelCol={{ span: 2 }}
                                wrapperCol={{ span: 20 }}
                                style={{ maxWidth: 600 }}
                                initialValues={{ remember: true }}
                                onFinish={toResetPwd}
                                autoComplete="off"
                            >
                                <Form.Item
                                    label=" "
                                    colon={false}
                                    name="email"
                                    rules={[{ required: true, message: 'Please input your username!' }]}
                                >
                                    <Input placeholder={"邮箱"} />
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
                                    name="checkPassword"
                                    rules={[{ required: true, message: 'Please input your password!' }]}
                                >
                                    <Input.Password placeholder={"确认密码"} />
                                </Form.Item>

                                <Form.Item
                                    label=" "
                                    colon={false}
                                    name="passCode"
                                    rules={[{ required: true, message: 'Please input your username!' }]}
                                >
                                    <Space>
                                        <Input placeholder={"验证码"} />
                                        <Button htmlType="button" className={styles.getPassCode} onClick={getPassCode}>
                                            获取验证码
                                        </Button>
                                    </Space>
                                </Form.Item>

                                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                                    <Space>
                                        <Button type="primary" htmlType="submit">
                                            重置
                                        </Button>
                                        <Button htmlType="reset">
                                            清空
                                        </Button>
                                    </Space>
                                </Form.Item>
                            </Form>
                        </div>
                    </Modal>
                </div>
            </div>
        </div>
    );
}

export default EntrancePage;
