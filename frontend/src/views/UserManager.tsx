import React, { useEffect, useState } from 'react';
import {message, Table} from 'antd';
import type { ColumnsType, TablePaginationConfig } from 'antd/es/table';
import type { FilterValue, SorterResult } from 'antd/es/table/interface';
import request from "../api/request";
import qs from 'qs';
import result from "../types/axios";

interface DataType {
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
        render: (name) => `${name.first} ${name.last}`,
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
    // {
    //     title: 'Gender',
    //     dataIndex: 'gender',
    //     filters: [
    //         { text: 'Male', value: 'male' },
    //         { text: 'Female', value: 'female' },
    //     ],
    //     width: '20%',
    // },
    // {
    //     title: 'Email',
    //     dataIndex: 'email',
    // },
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
            pageSize: 0,
        },
    });

    const fetchData = async () => {
        setLoading(true);
        const res: result = await request.get("/user/manager/getPages", {
            params: {
                email: "",
                userName: "",
                telephone: "",
                status: "",
                userType: "",
                createTimeFirst: "",
                createTimeLast: "",
                current: 1,
                size: 10,
            }
        });
        if (res.code !== 200) {
            message.error(res.msg);
            return;
        }

        const userTable = JSON.parse(JSON.stringify(res.data));
        setData(userTable.data);
        setLoading(false);
        setTableParams({
            ...tableParams,
            pagination: {
                ...tableParams.pagination,
                total: userTable.total,
                // 200 is mock data, you should read it from server
                // total: data.totalCount,
            },
        });


        // fetch(`https://randomuser.me/api?${qs.stringify(getRandomuserParams(tableParams))}`)
        //     .then((res) => res.json())
        //     .then(({ results }) => {
        //         setData(results);
        //         setLoading(false);
        //         setTableParams({
        //             ...tableParams,
        //             pagination: {
        //                 ...tableParams.pagination,
        //                 total: 200,
        //                 // 200 is mock data, you should read it from server
        //                 // total: data.totalCount,
        //             },
        //         });
        //     });
    };

    useEffect(() => {
        fetchData();
    }, [JSON.stringify(tableParams)]);

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

    return (
        <Table
            columns={columns}
            rowKey={(record) => record.email}
            dataSource={data}
            pagination={tableParams.pagination}
            loading={loading}
            // onChange={handleTableChange}
            bordered
        />
    );
};

export default UserManager;