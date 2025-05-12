use std::{fs, thread, time::Duration};

// 温度
const TEMP: &str = "温度";
const FILE_PATH_TEMP: &str = "/sys/class/thermal/thermal_zone0/temp";
pub fn get_temp() -> Result<i32, String> {
    fs::read_to_string(FILE_PATH_TEMP)
        .map_err(|err| format!("{}取得に失敗しました。{}", TEMP, err))?
        .trim()
        .parse()
        .map_err(|err| format!("{}変換に失敗しました。{}", TEMP, err))
}

// クロック
const FREQ: &str = "クロック";
const FILE_PATH_FREQ: &str = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
pub fn get_freq() -> Result<i32, String> {
    fs::read_to_string(FILE_PATH_FREQ)
        .map_err(|err| format!("{}取得に失敗しました。{}", FREQ, err))?
        .trim()
        .parse()
        .map_err(|err| format!("{}変換に失敗しました。{}", FREQ, err))
}

// cpu
#[derive(Debug)]
pub struct CpuInfo {
    pub cpu: f64,
    pub cpu0: f64,
    pub cpu1: f64,
    pub cpu2: f64,
    pub cpu3: f64,
}
const CPU: &str = "CPU";
const FILE_PATH_CPU: &str = "/proc/stat";
pub fn get_cpu() -> Result<CpuInfo, String> {
    let data = fs::read_to_string(FILE_PATH_CPU)
        .map_err(|err| format!("{}取得に失敗しました。{}", CPU, err))?;
    let pre_cpu = get_cpu_value(&data, "cpu")?;
    let pre_cpu0 = get_cpu_value(&data, "cpu0")?;
    let pre_cpu1 = get_cpu_value(&data, "cpu1")?;
    let pre_cpu2 = get_cpu_value(&data, "cpu2")?;
    let pre_cpu3 = get_cpu_value(&data, "cpu3")?;

    thread::sleep(Duration::from_secs(1));

    let data = fs::read_to_string(FILE_PATH_CPU)
        .map_err(|err| format!("{}取得に失敗しました。{}", CPU, err))?;
    let post_cpu = get_cpu_value(&data, "cpu")?;
    let post_cpu0 = get_cpu_value(&data, "cpu0")?;
    let post_cpu1 = get_cpu_value(&data, "cpu1")?;
    let post_cpu2 = get_cpu_value(&data, "cpu2")?;
    let post_cpu3 = get_cpu_value(&data, "cpu3")?;

    Ok(CpuInfo {
        cpu: calc_cpu(pre_cpu, post_cpu),
        cpu0: calc_cpu(pre_cpu0, post_cpu0),
        cpu1: calc_cpu(pre_cpu1, post_cpu1),
        cpu2: calc_cpu(pre_cpu2, post_cpu2),
        cpu3: calc_cpu(pre_cpu3, post_cpu3),
    })
}
pub fn calc_cpu((pre_idle, pre_total): (u32, u32), (post_idle, post_total): (u32, u32)) -> f64 {
    let delta_idle = post_idle - pre_idle;
    let delta_total = post_total - pre_total;

    100.0 * (delta_total - delta_idle) as f64 / delta_total as f64
}
pub fn get_cpu_value(data: &str, target: &str) -> Result<(u32, u32), String> {
    let data = get_line(data, target)?;
    let data: Vec<u32> = data
        .split_whitespace()
        .filter_map(|word| word.parse().ok())
        .collect();
    let idle = data[3] + data[4];
    let total = data.iter().sum();
    Ok((idle, total))
}

// メモリ
#[derive(Debug)]
pub struct MemInfo {
    pub mem_total: u32,
    pub mem_free: u32,
    pub buffers: u32,
    pub cached: u32,
    pub active: u32,
    pub inactive: u32,
}
const MEM: &str = "メモリ";
const FILE_PATH_MEM: &str = "/proc/meminfo";
pub fn get_mem() -> Result<MemInfo, String> {
    let data = fs::read_to_string(FILE_PATH_MEM)
        .map_err(|err| format!("{}取得に失敗しました。{}", MEM, err))?;
    Ok(MemInfo {
        mem_total: get_mem_value(&data, "MemTotal")?,
        mem_free: get_mem_value(&data, "MemFree")?,
        buffers: get_mem_value(&data, "Buffers")?,
        cached: get_mem_value(&data, "Cached")?,
        active: get_mem_value(&data, "Active")?,
        inactive: get_mem_value(&data, "Inactive")?,
    })
}
pub fn get_mem_value(data: &str, target: &str) -> Result<u32, String> {
    let data = get_line(data, target)?;
    let data: Vec<u32> = data
        .split_whitespace()
        .filter_map(|word| word.parse().ok())
        .collect();
    match data.first() {
        Some(v) => Ok(*v),
        None => Err(format!("{}の値が見つかりませんでした", target)),
    }
}

pub fn get_line(data: &str, target: &str) -> Result<String, String> {
    for line in data.lines() {
        if line.contains(target) {
            return Ok(line.to_string());
        }
    }
    Err(format!("{}が見つかりませんでした", target))
}
