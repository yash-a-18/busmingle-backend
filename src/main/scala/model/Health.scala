package busmingle.model

import zio.json.JsonCodec
import zio.json.DeriveJsonCodec

case class Health(status: String)

object Health:
    given codec: JsonCodec[Health] = DeriveJsonCodec.gen[Health]