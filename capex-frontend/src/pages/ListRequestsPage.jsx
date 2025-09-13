import useRequests from "../hooks/useRequests.js";
import {Toast} from "primereact/toast";
import {useRef, useState} from "react";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import Button from "../components/Button.jsx";

const ListRequestsPage = () => {
    const [lazyState, setLazyState] = useState({
        first: 0,
        rows: 2,
        page: 0,
        sortField: null,
        sortOrder: null,
    });
    const { isLoading, data, error } = useRequests(lazyState.page, lazyState.rows, lazyState.sortField, lazyState.sortOrder)
    const toast = useRef(null);


    const totalRecords = data ? data.page.totalElements : 0;

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

    return <>
        <Toast ref={toast}/>
        <h1 className="text-2xl font-bold">List Requests</h1>

        <DataTable value={data ? data.content : []} lazy dataKey="id" paginator
                   first={lazyState.first} rows={lazyState.rows} totalRecords={totalRecords} onPage={onPage}
                   onSort={onSort} sortField={lazyState.sortField} sortOrder={lazyState.sortOrder}
                   loading={isLoading} tableStyle={{ minWidth: '75rem' }}
                   rowClassName={(rowData) => ({ '!bg-red-50': rowData?.emergency })}
                   >
            <Column field="requestNumber" header="Number" sortable filter filterPlaceholder="Search" />
            <Column field="type" sortable filter header="Type" filterPlaceholder="Search" />
            <Column field="projectName" sortable filter header="Project Name" filterPlaceholder="Search" />
            <Column field="projectDate" sortable filter header="Project Date" filterPlaceholder="Search" />
            <Column field="capexCost" sortable filter header="Capex" filterPlaceholder="Search" />
            <Column field="opexCost" sortable filter header="Opex" filterPlaceholder="Search" />
            <Column field="parent.requestNumber" sortable header="Parent" filter filterPlaceholder="Search" />
            <Column body={actionBodyTemplate} />
        </DataTable>
    </>
}

export default ListRequestsPage