use axum::{Json, http::StatusCode, response::IntoResponse};
use device::{CpuInfo, MemInfo};
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
                    cpu: 60.0,
                    cpu0: 60.0,
                    cpu1: 60.0,
                    cpu2: 60.0,
                    cpu3: 60.0,
                }
            } else {
                return error_500::create_response();
            }
        }
    };
    let cpu = CpuInfoResponse {
        cpu: cpu.cpu,
        cpu0: cpu.cpu0,
        cpu1: cpu.cpu1,
        cpu2: cpu.cpu2,
        cpu3: cpu.cpu3,
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

    (
        StatusCode::OK,
        Json(DeviceResponse {
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
    temp: i32,
    freq: i32,
    cpu: CpuInfoResponse,
    mem: MemInfoResponse,
}

#[derive(Serialize)]
pub struct CpuInfoResponse {
    cpu: f64,
    cpu0: f64,
    cpu1: f64,
    cpu2: f64,
    cpu3: f64,
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
