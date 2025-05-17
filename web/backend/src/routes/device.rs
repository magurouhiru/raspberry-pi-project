use axum::{Json, http::StatusCode, response::IntoResponse};
use chrono::{DateTime, Utc};
use device::{CpuDetailInfo, CpuInfo, MemInfo};
use serde::Serialize;

use crate::errors::error_500;

pub async fn get() -> impl IntoResponse {
    let temp = match device::get_temp() {
        Ok(v) => v,
        Err(_) => {
            if cfg!(debug_assertions) {
                rand::random_range(60000..120000)
            } else {
                return error_500::create_response();
            }
        }
    };
    let freq = match device::get_freq() {
        Ok(v) => v,
        Err(_) => {
            if cfg!(debug_assertions) {
                rand::random_range(600000..1200000)
            } else {
                return error_500::create_response();
            }
        }
    };
    let cpu = match device::get_cpu() {
        Ok(v) => v,
        Err(_) => {
            if cfg!(debug_assertions) {
                CpuInfo {
                    cpu: CpuDetailInfo {
                        idle: 50,
                        total: 100,
                    },
                    cpu0: CpuDetailInfo {
                        idle: 10,
                        total: 110,
                    },
                    cpu1: CpuDetailInfo {
                        idle: 20,
                        total: 120,
                    },
                    cpu2: CpuDetailInfo {
                        idle: 30,
                        total: 130,
                    },
                    cpu3: CpuDetailInfo {
                        idle: 40,
                        total: 140,
                    },
                }
            } else {
                return error_500::create_response();
            }
        }
    };
    let cpu = CpuInfoResponse {
        cpu: CpuDetailInfoResponse {
            total: cpu.cpu.total,
            idle: cpu.cpu.idle,
        },
        cpu0: CpuDetailInfoResponse {
            total: cpu.cpu0.total,
            idle: cpu.cpu0.idle,
        },
        cpu1: CpuDetailInfoResponse {
            total: cpu.cpu1.total,
            idle: cpu.cpu1.idle,
        },
        cpu2: CpuDetailInfoResponse {
            total: cpu.cpu2.total,
            idle: cpu.cpu2.idle,
        },
        cpu3: CpuDetailInfoResponse {
            total: cpu.cpu3.total,
            idle: cpu.cpu3.idle,
        },
    };
    let mem = match device::get_mem() {
        Ok(v) => v,
        Err(_) => {
            if cfg!(debug_assertions) {
                MemInfo {
                    mem_total: 943204,
                    mem_free: 943204,
                    buffers: 943204,
                    cached: 943204,
                    active: 943204,
                    inactive: 943204,
                }
            } else {
                return error_500::create_response();
            }
        }
    };
    let mem = MemInfoResponse {
        mem_total: mem.mem_total,
        mem_free: mem.mem_free,
        buffers: mem.buffers,
        cached: mem.cached,
        active: mem.active,
        inactive: mem.inactive,
    };

    let utc_datetime: DateTime<Utc> = Utc::now();

    (
        StatusCode::OK,
        Json(DeviceResponse {
            timestamp: utc_datetime.to_string(),
            temp,
            freq,
            cpu,
            mem,
        }),
    )
        .into_response()
}

#[derive(Serialize)]
pub struct DeviceResponse {
    timestamp: String,
    temp: i32,
    freq: i32,
    cpu: CpuInfoResponse,
    mem: MemInfoResponse,
}

#[derive(Serialize)]
pub struct CpuInfoResponse {
    cpu: CpuDetailInfoResponse,
    cpu0: CpuDetailInfoResponse,
    cpu1: CpuDetailInfoResponse,
    cpu2: CpuDetailInfoResponse,
    cpu3: CpuDetailInfoResponse,
}

#[derive(Serialize)]
pub struct CpuDetailInfoResponse {
    pub total: u32,
    pub idle: u32,
}

#[derive(Serialize)]
pub struct MemInfoResponse {
    mem_total: u32,
    mem_free: u32,
    buffers: u32,
    cached: u32,
    active: u32,
    inactive: u32,
}
