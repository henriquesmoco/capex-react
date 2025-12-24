import {useAxios} from "./useAxios.js";
import {useQuery} from "@tanstack/react-query";

const useCategories = () => {
    const axios = useAxios();
    const fetchCategories = async () => {
        const { data } = await axios.get(`/api/categories`)
        return data;
    }

    return useQuery({ queryKey: ['categories'], queryFn: fetchCategories});
}

export default useCategories;