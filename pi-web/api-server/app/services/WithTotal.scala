package services

case class WithTotal[T](total: Long, items: Seq[T])
