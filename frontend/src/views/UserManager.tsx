import React, {useEffect, useState} from 'react';
import {Button, Form, Input, message, Select, Space, Table} from 'antd';
import type {ColumnsType, TablePaginationConfig} from 'antd/es/table';
import type {FilterValue, SorterResult} from 'antd/es/table/interface';
import request from "../api/request";
import {result, userManagerSearch} from "../types/axios";
import {UserStatus, UserType} from "../types/enum";
import {MailFilled, UserOutlined} from "@ant-design/icons";

interface DataType {
    id: string;
    email: string;
    userName: string;
    telephone: string;
    status: string;
    userType: string;
    createBy: string;
    createTime: string;
}

interface TableParams {
    pagination?: TablePaginationConfig;
    sortField?: string;
    sortOrder?: string;
    filters?: Record<string, FilterValue>;
}

const columns: ColumnsType<DataType> = [
    {
        title: '邮箱',
        dataIndex: 'email',
        sorter: true,
        width: '20%',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
        sorter: true,
        width: '20%',
    },
    {
        title: '电话',
        dataIndex: 'telephone',
        sorter: true,
        width: '20%',
    },
    {
        title: '用户状态',
        dataIndex: 'status',
        sorter: true,
        width: '20%',
    },
    {
        title: '用户类型',
        dataIndex: 'userType',
        sorter: true,
        width: '20%',
    },
    {
        title: '创建人',
        dataIndex: 'createBy',
        sorter: true,
        width: '20%',
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        sorter: true,
        width: '20%',
    },
];

const getRandomuserParams = (params: TableParams) => ({
    results: params.pagination?.pageSize,
    page: params.pagination?.current,
    ...params,
});

const UserManager: React.FC = () => {
    const [data, setData] = useState<DataType[]>();
    const [loading, setLoading] = useState(false);
    const [tableParams, setTableParams] = useState<TableParams>({
        pagination: {
            current: 1,
            pageSize: 10,
            showSizeChanger: true,
            showQuickJumper: true,
        },
    });
    const [hasSelected, setHasSelected] = useState([]);
    const [searchForm] = Form.useForm();
    const [searchItems, setSearchItems] = useState<userManagerSearch>({
        email: "",
        userName: "",
        status: UserStatus.Wrong,
        userType: UserType.Wrong
    });

    const fetchData = async () => {
        setLoading(true);
        const res: result = await request.get("/userManager/getPages", {
            params: {
                ...searchItems,
                current: tableParams.pagination?.current,
                size: tableParams.pagination?.pageSize,
                createTimeFirst: "",
                createTimeLast: "",
                telephone: ""
            }
        });

        if (res.code !== 200) {
            message.error(res.msg);
            return;
        }

        const userTable = JSON.parse(JSON.stringify(res.data));
        const userData = userTable.page.records;
        for (let userDatum of userData) {
            // @ts-ignore
            userDatum.status = UserStatus[userDatum.status];
            // @ts-ignore
            userDatum.userType = UserType[userDatum.userType];
        }
        setData(userData);
        setLoading(false);
        setTableParams({
            ...tableParams,
            pagination: {
                ...tableParams.pagination,
                total: userTable.total,
                showTotal: (total) => `共 ${total} 条`,
            },
        });
    };

    useEffect(() => {
        fetchData();
    }, [JSON.stringify(tableParams), searchItems]);

    const handleTableChange = (
        pagination: TablePaginationConfig,
        filters: Record<string, FilterValue>,
        sorter: SorterResult<DataType>,
    ) => {
        setTableParams({
            pagination,
            filters,
            ...sorter,
        });

        // `dataSource` is useless since `pageSize` changed
        if (pagination.pageSize !== tableParams.pagination?.pageSize) {
            setData([]);
        }
    };

    const rowSelection = {
        onChange: (selectedRowKeys: any, selectedRows: any) => {
            setHasSelected(selectedRowKeys);
        },
    }

    const toSearch = (values: any) => {
        values.email = values.email === undefined ? "" : values.email;
        values.userName = values.userName === undefined ? "" : values.userName;
        values.status = values.userStatus === undefined ? UserStatus.Wrong : values.userStatus;
        values.userType = values.userType === undefined ? UserType.Wrong : values.userType;
        setSearchItems(values);
    }

    return (
        <div>
            <div style={{marginBottom: '10px'}}>
                <Form
                    form={searchForm}
                    name="searchForm"
                    requiredMark={false}
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                    style={{ maxWidth: 600 }}
                    initialValues={{ remember: true }}
                    onFinish={toSearch}
                    autoComplete="off"
                >

                    <Space>
                        <Form.Item
                            label=" "
                            colon={false}
                            name="email"
                            style={{marginLeft: '-20px'}}
                        >
                            <Input style={{ width: 230 }} placeholder={"邮箱"} suffix={<MailFilled/>} />
                        </Form.Item>

                        <Form.Item
                            label=" "
                            colon={false}
                            name="userName"
                            style={{marginLeft: '10px'}}
                        >
                            <Input style={{ width: 230 }} placeholder={"用户名"} suffix={<UserOutlined />} />
                        </Form.Item>

                        <Form.Item
                            label=" "
                            colon={false}
                            name="userStatus"
                            style={{marginLeft: '20px'}}
                        >
                            <Select
                                placeholder={"用户状态"}
                                style={{ width: 120 }}
                                allowClear
                                options={[{ value: 'Normal', label: '正常' }, { value: 'Block', label: '封禁' }]}
                            />
                        </Form.Item>

                        <Form.Item
                            label=" "
                            colon={false}
                            name="userType"
                            style={{marginLeft: '10px'}}
                        >
                            <Select
                                placeholder={"用户类型"}
                                style={{ width: 120 }}
                                allowClear
                                options={[{ value: 'Admin', label: '管理员' }, { value: 'Worker', label: '员工' }, { value: 'Customer', label: '顾客' }]}
                            />
                        </Form.Item>

                        <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                            <Space>
                                <Button type="primary" htmlType="submit">
                                    搜索
                                </Button>
                                <Button htmlType="reset">
                                    重置
                                </Button>
                            </Space>
                        </Form.Item>
                    </Space>
                </Form>
            </div>
            <Table
                columns={columns}
                rowKey={(record) => record.id}
                dataSource={data}
                pagination={tableParams.pagination}
                loading={loading}
                rowSelection={rowSelection}
                // @ts-ignore
                onChange={handleTableChange}
                bordered
            />
        </div>
    );
};

export default UserManager;