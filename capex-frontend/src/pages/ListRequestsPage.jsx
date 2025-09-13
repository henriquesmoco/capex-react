import useRequests from "../hooks/useRequests.js";
import {Toast} from "primereact/toast";
import {useRef, useState} from "react";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import Button from "../components/Button.jsx";

const ListRequestsPage = () => {
    const { isLoading, data, error } = useRequests()
    const toast = useRef(null);
    const [lazyState, setLazyState] = useState({
        first: 0,
        rows: 10,
        page: 1,
        sortField: null,
        sortOrder: null,
        /*filters: {
            name: { value: '', matchMode: 'contains' },
            'country.name': { value: '', matchMode: 'contains' },
            company: { value: '', matchMode: 'contains' },
            'representative.name': { value: '', matchMode: 'contains' }
        }*/
    });

    //TODO change this to info from backend
    const totalRecords = data ? data.length : 0;

    const onPage = (event) => {
        setLazyState(event);
    };

    const onSort = (event) => {
        setLazyState(event);
    };

    if(isLoading) {
        return <>
            <Toast ref={toast}/>
            <h1>Loading...</h1>
        </>
    }

    if (error) {
        toast.current.show({severity: 'error', summary: 'Error', detail: error.message, life: 3000});
    }

    const actionBodyTemplate = (rowData) => {
        console.log(rowData)
        return <Button label={rowData.id}></Button>;
    };

    return <>
        <Toast ref={toast}/>
        <h1 className="text-2xl font-bold">List Requests</h1>

        <DataTable value={data} lazy dataKey="id" paginator
                   first={lazyState.first} rows={lazyState.rows} totalRecords={totalRecords} onPage={onPage}
                   onSort={onSort} sortField={lazyState.sortField} sortOrder={lazyState.sortOrder}
                   loading={isLoading} tableStyle={{ minWidth: '75rem' }}

                   >
            <Column field="requestNumber" header="Name" sortable filter filterPlaceholder="Search" />
            <Column field="type" sortable filter header="Type" filterPlaceholder="Search" />
            <Column field="parent.requestNumber" sortable header="Parent" filter filterPlaceholder="Search" />
            <Column body={actionBodyTemplate} />
        </DataTable>
    </>
}

export default ListRequestsPage