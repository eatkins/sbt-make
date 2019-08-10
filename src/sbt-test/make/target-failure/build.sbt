p"foo.in" :- p"foo.in.template" build {
  val content = IO.read(p"foo.in.template".toFile)
  if (content == "fail") throw new IllegalStateException("fail transitive")
  else IO.write(p"foo.in".toFile, content)
}

p"foo.out" :- p"foo.in" build {
  val content = IO.read(p"foo.in".toFile)
  if (content == "fail") throw new IllegalStateException("fail")
  else IO.write(p"foo.out".toFile, content)
}

InputKey[Unit]("check") := {
  val expected = Def.spaceDelimited().parsed.mkString
  assert(IO.read(p"foo.out".make.toFile) == expected)
}


