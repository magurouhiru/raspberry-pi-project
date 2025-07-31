package repositories

import java.time.Instant
import javax.inject._

import api.DBDeviceCpuInfoResponse
import models._

@Singleton
class DeviceRepository @Inject() (quillCtx: QuillContext) {
  import quillCtx.ctx._

  def readSizeTemp(): Long = run(query[DeviceTempInfo].size)

  def readTemp(id: Int): Option[DeviceTempInfo] =
    run(query[DeviceTempInfo].filter(_.id == lift(id))).headOption

  def readTemp(offset: Int, limit: Int): Seq[DeviceTempInfo] =
    run(query[DeviceTempInfo].drop(lift(offset)).take(lift(limit)))

  def createTemp(temp: api.DeviceTempInfoResponse): Int = run(
    query[DeviceTempInfo]
      .insert(_.timestamp -> lift(temp.timestamp), _.temp -> lift(temp.temp))
      .returningGenerated(_.id),
  )

  def readSizeFreq(): Long = run(query[DeviceFreqInfo].size)

  def readFreq(id: Int): Option[DeviceFreqInfo] =
    run(query[DeviceFreqInfo].filter(_.id == lift(id))).headOption

  def readFreq(offset: Int, limit: Int): Seq[DeviceFreqInfo] =
    run(query[DeviceFreqInfo].drop(lift(offset)).take(lift(limit)))

  def createFreq(freq: api.DeviceFreqInfoResponse): Int = run(
    query[DeviceFreqInfo]
      .insert(_.timestamp -> lift(freq.timestamp), _.freq -> lift(freq.freq))
      .returningGenerated(_.id),
  )

  def readSizeCpuDetailRaw(): Long = run(query[DeviceCpuDetailInfo].size)

  def readCpuDetailRaw(id: Int): Option[DeviceCpuDetailInfo] =
    run(query[DeviceCpuDetailInfo].filter(_.id == lift(id))).headOption

  def readCpuDetailRaw(offset: Int, limit: Int): Seq[DeviceCpuDetailInfo] =
    run(query[DeviceCpuDetailInfo].drop(lift(offset)).take(lift(limit)))

  def createCpuDetailRaw(
      cpu_info_id: Int,
      cpu_number: Int,
      total: Int,
      idle: Int,
  ): Int = run(
    query[DeviceCpuDetailInfo]
      .insert(
        _.cpu_info_id -> lift(cpu_info_id),
        _.cpu_number -> lift(cpu_number),
        _.total -> lift(total),
        _.idle -> lift(idle),
      )
      .returningGenerated(_.id),
  )

  def readSizeCpuRaw(): Long = run(query[DeviceCpuInfo].size)

  def readCpuRaw(id: Int): Option[DeviceCpuInfo] =
    run(query[DeviceCpuInfo].filter(_.id == lift(id))).headOption

  def readCpuRaw(offset: Int, limit: Int): Seq[DeviceCpuInfo] =
    run(query[DeviceCpuInfo].drop(lift(offset)).take(lift(limit)))

  def createCpuRaw(req: api.DeviceCpuInfoResponse): Int = run(
    query[DeviceCpuInfo]
      .insert(_.timestamp -> lift(req.timestamp))
      .returningGenerated(_.id),
  )

  def readCpu(id: Int): Option[DBDeviceCpuInfoResponse] = run {
    for {
      cpu <- query[DeviceCpuInfo].filter(_.id == lift(id))
      cpu_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == -1,
      )
      cpu0_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == 0,
      )
      cpu1_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == 1,
      )
      cpu2_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == 2,
      )
      cpu3_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == 3,
      )
    } yield DBDeviceCpuInfoResponse(
      cpu.id,
      cpu.timestamp,
      api.DeviceCpuDetailInfoResponse(cpu_detail.total, cpu_detail.idle),
      api.DeviceCpuDetailInfoResponse(cpu0_detail.total, cpu0_detail.idle),
      api.DeviceCpuDetailInfoResponse(cpu1_detail.total, cpu1_detail.idle),
      api.DeviceCpuDetailInfoResponse(cpu2_detail.total, cpu2_detail.idle),
      api.DeviceCpuDetailInfoResponse(cpu3_detail.total, cpu3_detail.idle),
    )
  }.headOption

  def readCpu(offset: Int, limit: Int): Seq[DBDeviceCpuInfoResponse] = run {
    for {
      cpu <- query[DeviceCpuInfo].drop(lift(offset)).take(lift(limit))
      cpu_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == -1,
      )
      cpu0_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == 0,
      )
      cpu1_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == 1,
      )
      cpu2_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == 2,
      )
      cpu3_detail <- query[DeviceCpuDetailInfo].filter(d =>
        d.cpu_info_id == cpu.id && d.cpu_number == 3,
      )
    } yield DBDeviceCpuInfoResponse(
      cpu.id,
      cpu.timestamp,
      api.DeviceCpuDetailInfoResponse(cpu_detail.total, cpu_detail.idle),
      api.DeviceCpuDetailInfoResponse(cpu0_detail.total, cpu0_detail.idle),
      api.DeviceCpuDetailInfoResponse(cpu1_detail.total, cpu1_detail.idle),
      api.DeviceCpuDetailInfoResponse(cpu2_detail.total, cpu2_detail.idle),
      api.DeviceCpuDetailInfoResponse(cpu3_detail.total, cpu3_detail.idle),
    )
  }

  def readSizeMem(): Long = run(query[DeviceMemInfo].size)

  def readMem(id: Int): Option[DeviceMemInfo] =
    run(query[DeviceMemInfo].filter(_.id == lift(id))).headOption

  def readMem(offset: Int, limit: Int): Seq[DeviceMemInfo] =
    run(query[DeviceMemInfo].drop(lift(offset)).take(lift(limit)))

  def createMem(mem: api.DeviceMemInfoResponse): Int = run(
    query[DeviceMemInfo]
      .insert(
        _.timestamp -> lift(mem.timestamp),
        _.mem_total -> lift(mem.mem_total),
        _.mem_free -> lift(mem.mem_free),
        _.buffers -> lift(mem.buffers),
        _.cached -> lift(mem.cached),
        _.active -> lift(mem.active),
        _.inactive -> lift(mem.inactive),
      )
      .returningGenerated(_.id),
  )

}
