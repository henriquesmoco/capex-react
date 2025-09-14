import {useAxios} from "./useAxios.js";
import {useQuery} from "@tanstack/react-query";

const useRequests = (page, size, sortField, sortDirection, queryFilter) => {
    const axios = useAxios();
    const fetchAllRequests = async () => {
        const sort = sortField ? `&sort=${sortField},${sortDirection === 1 ? 'asc' : 'desc'}` : '';
        const { data } = await axios.get(`/api/requests?page=${page}&size=${size}${sort}${queryFilter}`) //?page=0&size=10&sort=name,asc
        return data;
    }

    return useQuery({ queryKey: ['requests', page, sortField, sortDirection, queryFilter], queryFn: fetchAllRequests});
}

export default useRequests;