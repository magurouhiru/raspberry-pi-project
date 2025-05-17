pub mod db {
    use chrono::NaiveDateTime;
    use serde::Serialize;

    #[derive(Serialize)]
    pub struct AnyDb {
        pub tables: Vec<AnyTable>,
    }

    #[derive(Serialize)]
    pub struct AnyTable {
        pub name: String,
        pub label: Vec<String>,
        pub data: Vec<Vec<String>>,
    }

    #[derive(sqlx::FromRow, Debug)]
    pub struct Hello {
        pub id: u32,
        pub timestamp: NaiveDateTime,
        pub message: String,
    }
}

pub mod device {
    use serde::Serialize;

    #[derive(Serialize)]
    pub struct DeviceResponse {
        pub timestamp: String,
        pub temp: i32,
        pub freq: i32,
        pub cpu: CpuInfoResponse,
        pub mem: MemInfoResponse,
    }

    #[derive(Serialize)]
    pub struct CpuInfoResponse {
        pub cpu: CpuDetailInfoResponse,
        pub cpu0: CpuDetailInfoResponse,
        pub cpu1: CpuDetailInfoResponse,
        pub cpu2: CpuDetailInfoResponse,
        pub cpu3: CpuDetailInfoResponse,
    }

    #[derive(Serialize)]
    pub struct CpuDetailInfoResponse {
        pub total: u32,
        pub idle: u32,
    }

    #[derive(Serialize)]
    pub struct MemInfoResponse {
        pub mem_total: u32,
        pub mem_free: u32,
        pub buffers: u32,
        pub cached: u32,
        pub active: u32,
        pub inactive: u32,
    }
}
