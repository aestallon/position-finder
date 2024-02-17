import axios, { type AxiosInstance } from 'axios';
import { ClientApi, Configuration, PositionApi} from '@/api/pfinder'

const instance: AxiosInstance = axios.create();
instance.interceptors.request.use((config) => {
    const token: string | null = localStorage.getItem("api-key");
    if (token) {
        config.headers.set('X-Aest-Token', token);
    }   
    return config;
});

const prefix: string = '/api/v1';
const configuration: Configuration = new Configuration();

export const clientApi = new ClientApi(configuration, prefix, instance);
export const positionApi = new PositionApi(configuration, prefix, instance);
