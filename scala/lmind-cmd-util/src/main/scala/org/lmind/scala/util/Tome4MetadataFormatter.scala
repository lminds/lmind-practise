package org.lmind.scala.util

import com.google.gson.Gson
import com.google.gson.JsonElement
import scala.reflect.io.File
import scala.io.Codec
import com.google.gson.JsonObject
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVFormat
import scala.util.parsing.json.JSON

object Tome4MetadataFormatter extends App {

  def get[T, T2](default: T2, fn: => T2): T2 = {
    try {
      return fn
    } catch {
      case ex: Exception => default
    }
  }

  val b = List.newBuilder[(List[String], (JsonObject) => List[String])]

  // 一般信息抽取
  b += ((List("define_as", "name", "material_level", "type", "subtype"), j => {
    val define_as = get(null, {
      j.getAsJsonPrimitive("define_as").getAsString()
    })
    val name = get(null, {
      j.getAsJsonPrimitive("name").getAsString()
    })
    val material_level = get(null, {
      j.getAsJsonPrimitive("material_level").getAsString()
    })
    val type1 = get(null, {
      j.getAsJsonPrimitive("type").getAsString()
    })
    val subtype = get(null, {
      j.getAsJsonPrimitive("subtype").getAsString()
    })

    List(define_as, name, material_level, type1, subtype)
  }))

  // 傷害强化抽取
  b += ((List("inc_damage_arcane", "inc_damage_cold", "inc_damage_fire", "inc_damage_lightning", "inc_damage_physical"), j => {
    val inc_damage = get(new JsonObject(), {
      j.getAsJsonObject("wielder").getAsJsonObject("inc_damage")
    })
    val arcane = get("", {
      inc_damage.getAsJsonPrimitive("ARCANE").getAsString()
    })
    val cold = get("", {
      inc_damage.getAsJsonPrimitive("COLD").getAsString()
    })
    val fire = get("", {
      inc_damage.getAsJsonPrimitive("FIRE").getAsString()
    })
    val lightning = get("", {
      inc_damage.getAsJsonPrimitive("LIGHTNING").getAsString()
    })
    val physical = get("", {
      inc_damage.getAsJsonPrimitive("PHYSICAL").getAsString()
    })
    List(arcane, cold, fire, lightning, physical)
  }))
  val extractors = b.result()

  val file = File("""D:\tome.txt""")
  val out = File("D:\\tome.csv")("utf-8")
  val pw = out.printWriter()

  // title
  val title = extractors.foldLeft(List.empty[String])((a, b) => a ::: b._1)
  pw.println(CSVFormat.EXCEL.format(title: _*));

  file.lines(Codec("utf-8")).foreach(line => {
    val z = new Gson().fromJson(line, classOf[JsonElement])
    
    val row = extractors.foldLeft(List.empty[String])((a, b) => a ::: b._2(z.getAsJsonObject()))
    pw.println(CSVFormat.EXCEL.format(row: _*));
  })

  pw.close()
  
}