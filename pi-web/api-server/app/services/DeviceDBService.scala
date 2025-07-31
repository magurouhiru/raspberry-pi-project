package services

import javax.inject.Inject
import javax.inject.Singleton

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import api.DBDeviceCpuInfoResponse
import models._
import repositories.DeviceRepository

@Singleton
class DeviceDBService @Inject() (repo: DeviceRepository)(implicit
    ec: ExecutionContext,
    quillCtx: QuillContext,
) extends DBServiceBase {

  def readSizeTemp(): Future[Either[ServiceError, Long]] =
    runAsyncTx(repo.readSizeTemp())

  def readTemp(
      offset: Int,
      limit: Int,
  ): Future[Either[ServiceError, WithTotal[DeviceTempInfo]]] =
    runAsyncTx(WithTotal(repo.readSizeTemp(), repo.readTemp(offset, limit)))

  def createTemp(
      temp: api.DeviceTempInfoResponse,
  ): Future[Either[ServiceError, Option[DeviceTempInfo]]] =
    runAsyncTx(repo.readTemp(repo.createTemp(temp)))

  def readSizeFreq(): Future[Either[ServiceError, Long]] =
    runAsyncTx(repo.readSizeFreq())

  def readFreq(
      offset: Int,
      limit: Int,
  ): Future[Either[ServiceError, WithTotal[DeviceFreqInfo]]] =
    runAsyncTx(WithTotal(repo.readSizeFreq(), repo.readFreq(offset, limit)))

  def createFreq(
      freq: api.DeviceFreqInfoResponse,
  ): Future[Either[ServiceError, Option[DeviceFreqInfo]]] =
    runAsyncTx(repo.readFreq(repo.createFreq(freq)))

  def readSizeCpuRaw(): Future[Either[ServiceError, Long]] =
    runAsyncTx(repo.readSizeCpuRaw())

  def readCpuDetailRaw(
      offset: Int,
      limit: Int,
  ): Future[Either[ServiceError, WithTotal[DeviceCpuDetailInfo]]] = runAsyncTx(
    WithTotal(repo.readSizeCpuDetailRaw(), repo.readCpuDetailRaw(offset, limit)),
  )

  def readCpuRaw(
      offset: Int,
      limit: Int,
  ): Future[Either[ServiceError, WithTotal[DeviceCpuInfo]]] =
    runAsyncTx(WithTotal(repo.readSizeCpuRaw(), repo.readCpuRaw(offset, limit)))

  def readCpu(
      offset: Int,
      limit: Int,
  ): Future[Either[ServiceError, WithTotal[DBDeviceCpuInfoResponse]]] =
    runAsyncTx(WithTotal(repo.readSizeCpuRaw(), repo.readCpu(offset, limit)))

  def createCpu(
      cpu: api.DeviceCpuInfoResponse,
  ): Future[Either[ServiceError, Option[DBDeviceCpuInfoResponse]]] =
    runAsyncTx {
      val cpu_id = repo.createCpuRaw(cpu)
      repo.createCpuDetailRaw(cpu_id, -1, cpu.cpu.total, cpu.cpu.idle)
      repo.createCpuDetailRaw(cpu_id, 0, cpu.cpu0.total, cpu.cpu0.idle)
      repo.createCpuDetailRaw(cpu_id, 1, cpu.cpu1.total, cpu.cpu1.idle)
      repo.createCpuDetailRaw(cpu_id, 2, cpu.cpu2.total, cpu.cpu2.idle)
      repo.createCpuDetailRaw(cpu_id, 3, cpu.cpu3.total, cpu.cpu3.idle)

      repo.readCpu(cpu_id)
    }

  def readSizeMem(): Future[Either[ServiceError, Long]] =
    runAsyncTx(repo.readSizeMem())

  def readMem(
      offset: Int,
      limit: Int,
  ): Future[Either[ServiceError, WithTotal[DeviceMemInfo]]] =
    runAsyncTx(WithTotal(repo.readSizeMem(), repo.readMem(offset, limit)))

  def createMem(
      mem: api.DeviceMemInfoResponse,
  ): Future[Either[ServiceError, Option[DeviceMemInfo]]] =
    runAsyncTx(repo.readMem(repo.createMem(mem)))

}
