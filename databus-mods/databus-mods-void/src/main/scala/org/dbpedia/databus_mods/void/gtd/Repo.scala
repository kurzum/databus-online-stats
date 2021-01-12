
package org.dbpedia.databus_mods.void.gtd

import better.files.File

class Repo(volume: String) {

  def createFile(dataIdFilePath: String, metadata: String = "mod.ttl"): java.io.File = {
    val file = File(volume) / dataIdFilePath / metadata
    file.createFileIfNotExists(createParents = true)
    file.toJava
  }

  def findFile(dataIdFilePath: String, metadata: String = "mod.ttl"): Option[File] = {
    val file = File(volume) / dataIdFilePath / metadata
    if (file.exists) {
      Some(file)
    } else {
      None
    }
  }

  def listDir(path: String): List[File] = {
    val dir = File(volume) / path
    dir.list.toList
  }
}
