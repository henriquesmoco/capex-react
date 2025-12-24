import useRequest from "../hooks/useRequest.js";
import {message, Skeleton} from "antd";
import {useParams} from "react-router";
import { Form, Input, Select, Switch, DatePicker, Button, Space, Row, Col } from 'antd';
import dayjs from 'dayjs';
import useBusinessUnits from "../hooks/useBusinessUnits.js";
import useCategories from "../hooks/useCategories.js";

const EditRequestPage = () => {
    const { id } = useParams();
    const { isLoading, data, error } = useRequest(id)
    const { isLoading: isLoadingBU, data: dataBU, error: errorBU } = useBusinessUnits()
    const { isLoading: isLoadingCAT, data: dataCAT, error: errorCAT } = useCategories()
    const [messageApi, contextHolder] = message.useMessage();
    const [form] = Form.useForm();

    if (error || errorBU || errorCAT) {
        messageApi.open({
            type: 'error',
            content: error.message || errorBU.message || errorCAT.message,
        });
    }

    const handleSave = () => {
        form.validateFields()
            .then(values => {
                // Here you would typically send the data to an API
                console.log('Form data to be saved:', values);
                alert('Form saved successfully!');
            })
            .catch(info => {
                console.log('Validate Failed:', info);
            });
    };

    const handleCancel = () => {
        // Reset form to initial data
        form.resetFields();
        console.log('Form cancelled and reset.');
        alert('Form cancelled and reset.');
    };

    if (isLoading) {
        return <>
            {contextHolder}
            <h1 className="text-2xl font-bold">Edit Request</h1>
            <Skeleton.Input active={true} />
        </>
    }

    return <>
        {contextHolder}
        <h1 className="text-2xl font-bold">Edit Request</h1>
        <div className={'p-5'}>
            <Row justify="space-between" align="middle" style={{ marginBottom: '24px' }}>
                <Col>
                    <h1 className={'text-4xl'}>{data.requestNumber}</h1>
                </Col>
                <Col>
                    <Space>
                        <Button type="primary" onClick={handleSave}>
                            Save
                        </Button>
                        <Button onClick={handleCancel}>
                            Cancel
                        </Button>
                    </Space>
                </Col>
            </Row>

            <Form
                form={form}
                layout="vertical"
                initialValues={{
                    ...data,
                    projectDate: data.projectDate ? dayjs(data.projectDate) : null,
                    category: data.category?.id,
                    businessUnit: data.businessUnit?.id,
                }}
            >
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item name="projectName" label="Project Name" rules={[{ required: true, message: 'Please enter a project name' }]}>
                            <Input />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item name="projectDate" label="Project Date">
                            <DatePicker style={{ width: '100%' }} />
                        </Form.Item>
                    </Col>
                </Row>

                <Form.Item name="description" label="Description">
                    <Input.TextArea rows={4} />
                </Form.Item>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item name="capexCost" label="Capex Cost">
                            <Input type="number" />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item name="opexCost" label="Opex Cost">
                            <Input type="number" />
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item name="category" label="Category">
                            <Select placeholder="Select a category">
                                {! isLoadingCAT && dataCAT.map(cat => (
                                    <Option key={cat.id} value={cat.id}>{cat.name}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item name="businessUnit" label="Business Unit">
                            <Select placeholder="Select a business unit">
                                {! isLoadingBU && dataBU.map(bu => (
                                    <Option key={bu.id} value={bu.id}>{bu.name}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                    </Col>
                </Row>

                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item name="emergency" label="Emergency" valuePropName="checked">
                            <Switch />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item name="itProject" label="IT Project" valuePropName="checked">
                            <Switch />
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
        </div>
    </>
}

export default EditRequestPage