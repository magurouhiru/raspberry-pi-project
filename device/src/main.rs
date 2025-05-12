fn main() {
    println!("{}", device::get_temp().unwrap());
    println!("{}", device::get_freq().unwrap());
    println!("{:?}", device::get_cpu().unwrap());
    println!("{:?}", device::get_mem().unwrap());
}
