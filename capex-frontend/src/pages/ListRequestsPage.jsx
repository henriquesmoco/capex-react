import useRequests from "../hooks/useRequests.js";
import {useState} from "react";
import { Table , message } from 'antd';
import TableFilterPopup from "../components/TableFilterPopup.jsx";
import dayjs from 'dayjs';

const parseFilters = (_filters) => {
    return Object.entries(_filters).
    filter(([_, value]) => value !== null && value !== undefined).
    flatMap(([field, item]) => {
        let formattedValue = item[0].value
        if (dayjs.isDayjs(item[0].value)) {
            formattedValue = item[0].value?.format('YYYY-MM-DD')
        }
        return `&filter=${field}(${item[0].operator})${encodeURI(formattedValue)}`
    }).join('')
}

const ListRequestsPage = () => {
    const [tableState, setTableState] = useState({
        page: 0,
        pageSize: 2,
        sortField: null,
        sortOrder: null,
    });
    const [filterState, setFilterState] = useState({})
    const queryFilters = parseFilters(filterState)
    const { isLoading, data, error } = useRequests(tableState.page, tableState.pageSize, tableState.sortField, tableState.sortOrder, queryFilters)
    const [messageApi, contextHolder] = message.useMessage();

    if (error) {
        messageApi.open({
            type: 'error',
            content: error.message,
        });
    }

    const columns = [
        {
            title: 'Number',
            dataIndex: 'requestNumber',
            key: 'requestNumber',
            sorter: true,
            render: (text) => <a>{text}</a>,
            filterDropdown: ({ setSelectedKeys, selectedKeys, confirm }) => (
                <TableFilterPopup dataType={"text"} setSelectedKeys={setSelectedKeys} selectedKeys={selectedKeys} onConfimr={confirm} />
            ),
            onFilter: (value, record) => true
        },
        {
            title: 'Type',
            dataIndex: 'type',
            key: 'type',
            sorter: true,
            filterDropdown: ({ setSelectedKeys, selectedKeys, confirm }) => (
                <TableFilterPopup dataType={"text"} setSelectedKeys={setSelectedKeys} selectedKeys={selectedKeys} onConfimr={confirm} />
            ),
            onFilter: (value, record) => true
        },
        {
            title: 'Project Name',
            dataIndex: 'projectName',
            key: 'projectName',
            sorter: true,
            filterDropdown: ({ setSelectedKeys, selectedKeys, confirm }) => (
                <TableFilterPopup dataType={"text"} setSelectedKeys={setSelectedKeys} selectedKeys={selectedKeys} onConfimr={confirm} />
            ),
            onFilter: (value, record) => true
        },
        {
            title: 'Project Date',
            dataIndex: 'projectDate',
            key: 'projectDate',
            sorter: true,
            filterDropdown: ({ setSelectedKeys, selectedKeys, confirm }) => (
                <TableFilterPopup dataType={"date"} setSelectedKeys={setSelectedKeys} selectedKeys={selectedKeys} onConfimr={confirm} />
            ),
            onFilter: (value, record) => true
        },
        {
            title: 'Capex',
            dataIndex: 'capexCost',
            key: 'capexCost',
            sorter: true,
            filterDropdown: ({ setSelectedKeys, selectedKeys, confirm }) => (
                <TableFilterPopup dataType={"number"} setSelectedKeys={setSelectedKeys} selectedKeys={selectedKeys} onConfimr={confirm} />
            ),
            onFilter: (value, record) => true
        },
        {
            title: 'Opex',
            dataIndex: 'opexCost',
            key: 'opexCost',
            sorter: true,
            filterDropdown: ({ setSelectedKeys, selectedKeys, confirm }) => (
                <TableFilterPopup dataType={"number"} setSelectedKeys={setSelectedKeys} selectedKeys={selectedKeys} onConfimr={confirm} />
            ),
            onFilter: (value, record) => true
        },
        ]

    return <>
        {contextHolder}
        <h1 className="text-2xl font-bold">List Requests</h1>
        <Table columns={columns}
               dataSource={data?.content || []} rowKey="id"
               loading={isLoading}
               pagination={{
                   current: tableState.page + 1,
                   pageSize: tableState.pageSize,
                   total: data?.page?.totalElements || 0
               }}
               onChange={(pagination, _filters, sorter) => {
                   setTableState({
                       page: pagination.current - 1,
                       pageSize: pagination.pageSize,
                       sortField: sorter.field,
                       sortOrder: sorter.order,
                   })
                   setFilterState(_filters)
               }}
        />
    </>
}

export default ListRequestsPage