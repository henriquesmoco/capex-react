import {useState} from "react";
import {Button, DatePicker, Input, Select} from "antd";

const operatorOptions = {
    text: [
        {value:'startsWith', label:'Starts With'},
        {value:'endsWith', label:'Ends With'},
        {value:'equals', label:'Equals'},
        {value:'notEquals', label:'Not Equals'},
        {value:'contains', label:'Contains'},
        {value:'notContains', label:'Not Contains'},
    ],
    number: [
        {value:'lt', label:'Less Than'},
        {value:'gt', label:'Greater Than'},
        {value:'lte', label:'Less Than or Equal'},
        {value:'gte', label:'Greater Than or Equal'},
    ],
    date: [
        {value:'dateIs', label:'Is'},
        {value:'dateIsNot', label:'Is Not'},
        {value:'dateBefore', label:'Before'},
        {value:'dateAfter', label:'After'},
    ],
};

const TableFilterPopup = ({dataType, selectedKeys, setSelectedKeys, onConfimr}) => {
    const [operator, setOperator] = useState(selectedKeys[0]?.operator || operatorOptions[dataType][0].value);
    const [value, setValue] = useState(selectedKeys[0]?.value || '');

    const applyFilter = () => {
        setSelectedKeys([{ operator, value }]);
        onConfimr();
    };

    const clearFilter = () => {
        setSelectedKeys([]);
        setOperator(operatorOptions[dataType][0].value);
        setValue('');
        onConfimr();
    }

    return (
        <div className={"flex flex-col gap-2 p-3"}>
            <Select
                value={operator}
                onChange={setOperator}
                options={operatorOptions[dataType]}
                className={"w-full"}
            >
            </Select>

            {dataType === 'text' && <Input
                value={value}
                onChange={e => setValue(e.target.value)}
                onPressEnter={applyFilter}
            />}

            {dataType === 'number' && <Input
                type="number"
                value={value}
                onChange={e => setValue(e.target.value)}
                onPressEnter={applyFilter}
            />}

            {dataType === 'date' && <DatePicker
                value={value}
                onChange={setValue}
            />}

            <div className={"flex flex-row gap-2 justify-end"}>
                <Button onClick={clearFilter} size="small" >
                    Clear
                </Button>
                <Button type="primary" onClick={applyFilter} size="small" >
                    Apply
                </Button>
            </div>
        </div>
    );
}

export default TableFilterPopup