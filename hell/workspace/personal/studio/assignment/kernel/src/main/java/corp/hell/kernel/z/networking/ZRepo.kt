package corp.hell.kernel.z.networking

import corp.hell.kernel.z.data.ReqZ
import javax.inject.Inject

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 27, 2021
 */
class ZRepo @Inject constructor(private val service: ZService) {

    fun methodName1(phone: String) = service.getApi(phone)

    fun methodName2(req: ReqZ) = service.postApi(req)
}