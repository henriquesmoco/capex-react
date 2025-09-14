import useRequests from "../hooks/useRequests.js";
import {Toast} from "primereact/toast";
import {useRef, useState} from "react";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import Button from "../components/Button.jsx";
import {Calendar} from "primereact/calendar";

const parseFilters = (filters) => {
    if (!filters) return "";
    let result = "";
    for (const [key, { constraints }] of Object.entries(filters)) {
        if (key === "null") continue;

        for (const { value, matchMode } of constraints) {
            if (value !== null) {
                let formattedValue;

                if (value instanceof Date) {
                    const y = value.getFullYear();
                    const m = String(value.getMonth() + 1).padStart(2, '0');
                    const d = String(value.getDate()).padStart(2, '0');
                    formattedValue = `${y}-${m}-${d}`
                } else {
                    formattedValue = value;
                }
                result += `&filter=${key}(${matchMode})${encodeURI(formattedValue)}`;
            }
        }
    }
    return result;
}

const ListRequestsPage = () => {
    const [lazyState, setLazyState] = useState({
        first: 0,
        rows: 2,
        page: 0,
        sortField: null,
        sortOrder: null,
    });
    const queryFilters = parseFilters(lazyState.filters)
    const { isLoading, data, error } = useRequests(lazyState.page, lazyState.rows, lazyState.sortField, lazyState.sortOrder, queryFilters)
    const toast = useRef(null);

    const totalRecords = data ? data.page.totalElements : 0;

    const onFilter = (event) => {
        setLazyState(event);
    };

    const onPage = (event) => {
        setLazyState(event);
    };

    const onSort = (event) => {
        setLazyState(event);
    };

    if (error) {
        toast.current.show({severity: 'error', summary: 'Error', detail: error.message, life: 3000});
    }

    const actionBodyTemplate = (rowData) => {
        //console.log(rowData)
        return <Button label={rowData.id}></Button>;
    };

    const filterElementDate = (props) => {
        const { filterModel, filterCallback } = props;
        return <Calendar showIcon
                         value={filterModel.value}
                         onChange={(e) => filterCallback(e.value, props.index)} />
    }

    return <>
        <Toast ref={toast}/>
        <h1 className="text-2xl font-bold">List Requests</h1>

        <DataTable value={data ? data.content : []} lazy dataKey="id" paginator onFilter={onFilter}
                   first={lazyState.first} rows={lazyState.rows} totalRecords={totalRecords} onPage={onPage}
                   onSort={onSort} sortField={lazyState.sortField} sortOrder={lazyState.sortOrder}
                   loading={isLoading} tableStyle={{ minWidth: '75rem' }}
                   rowClassName={(rowData) => ({ '!bg-red-50': rowData?.emergency })}
                   >
            <Column field="requestNumber" header="Number" sortable
                    filter filterPlaceholder="Search" showFilterOperator={false} showAddButton={false}/>
            <Column field="type" header="Type" sortable
                    filter filterPlaceholder="Search" showFilterOperator={false} showAddButton={false}/>
            <Column field="projectName" header="Project Name" sortable
                    filter filterPlaceholder="Search" showFilterOperator={false} showAddButton={false}/>
            <Column field="projectDate" header="Project Date" sortable
                    filter filterPlaceholder="Search" showFilterOperator={false}
                    filterElement={filterElementDate}
                    dataType="date"
            />
            <Column field="capexCost" header="Capex" sortable
                    filter filterPlaceholder="Search" showFilterOperator={false}
                    dataType="numeric"
            />
            <Column field="opexCost" header="Opex" sortable
                    filter filterPlaceholder="Search" showFilterOperator={false}
                    dataType="numeric"
            />
            <Column field="parent.requestNumber" header="Parent" sortable
                    filter filterPlaceholder="Search" showFilterOperator={false} showAddButton={false}
            />
            <Column body={actionBodyTemplate} />
        </DataTable>
    </>
}

export default ListRequestsPage